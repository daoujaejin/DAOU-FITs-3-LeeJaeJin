#include "dbConnect.h"

void get_db_data() {
    OCIEnv* envhp;
    OCIError* errhp;
    OCISvcCtx* svchp;
    OCISession* usrhp;
    OCIServer* srvhp;
    OCIStmt* stmthp;
    OCIDefine* def1 = NULL, * def2 = NULL, * def3 = NULL, * def4 = NULL, * def5 = NULL;
    sword status;

    OCIParam* param;
    char* username = "C##2";
    char* password = "1234"; //오라클 비밀번호는 각자 것으로 쓰세요.
    char* dbname = "localhost:1521/xe";
    // 환경 핸들 생성
    if (OCIEnvCreate(&envhp, OCI_DEFAULT, NULL, NULL, NULL, NULL, 0, NULL) !=
        OCI_SUCCESS) {
        printf("OCIEnvCreate failed\n");
        return -1;
    }
    // 오류 핸들 생성
    if (OCIHandleAlloc((dvoid*)envhp, (dvoid**)&errhp, OCI_HTYPE_ERROR, (size_t)0,
        (dvoid**)NULL) != OCI_SUCCESS) {
        printf("OCIHandleAlloc failed for error handle\n");
        return -1;
    }
    // 서버 핸들 생성
    if (OCIHandleAlloc((dvoid*)envhp, (dvoid**)&srvhp, OCI_HTYPE_SERVER, (size_t)0,
        (dvoid**)NULL) != OCI_SUCCESS) {
        printf("OCIHandleAlloc failed for server handle\n");
        return -1;
    }
    // 서버 연결
    if (OCIServerAttach(srvhp, errhp, (text*)dbname, strlen(dbname), OCI_DEFAULT) !=
        OCI_SUCCESS) {
        check_error(errhp);
        return -1;
    }
    // 서비스 컨텍스트 생성
    if (OCIHandleAlloc((dvoid*)envhp, (dvoid**)&svchp, OCI_HTYPE_SVCCTX, (size_t)0,
        (dvoid**)NULL) != OCI_SUCCESS) {
        check_error(errhp);
        return -1;
    }
    // 세션 핸들 생성 및 연결
    if (OCIHandleAlloc((dvoid*)envhp, (dvoid**)&usrhp, OCI_HTYPE_SESSION, (size_t)0,
        (dvoid**)NULL) != OCI_SUCCESS) {
        check_error(errhp);
        return -1;
    }
    if (OCILogon2(envhp, errhp, &svchp,
        (OraText*)username, (ub4)strlen(username),
        (OraText*)password, (ub4)strlen(password),
        (OraText*)dbname, (ub4)strlen(dbname),
        OCI_DEFAULT /* 마지막 인수 추가 */
    ) != OCI_SUCCESS) {
        check_error(errhp);
        return -1;
    }

    //printf("Oracle Database connected successfully.\n");

    // TRANSACTIONS 테이블의 데이터를 transactions 연결 리스트에 넣기
    char* select_sql = "SELECT customer_name, stock_name, trade_type, quantity, price FROM TRANSACTIONS";
    OCIHandleAlloc(envhp, (void**)&stmthp, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmthp, errhp, (text*)select_sql, strlen(select_sql), OCI_NTV_SYNTAX, OCI_DEFAULT);
    OCIStmtExecute(svchp, stmthp, errhp, 0, 0, NULL, NULL, OCI_DEFAULT);

    char customer_name[50];
    char stock_name[50];
    char trade_type[20];
    int quantity;
    double price;

    OCIDefineByPos(stmthp, &def1, errhp, 1, customer_name, sizeof(customer_name), SQLT_STR, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def2, errhp, 2, stock_name, sizeof(stock_name), SQLT_STR, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def3, errhp, 3, trade_type, sizeof(trade_type), SQLT_STR, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def4, errhp, 4, &quantity, sizeof(quantity), SQLT_INT, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def5, errhp, 5, &price, sizeof(price), SQLT_FLT, NULL, NULL, NULL, OCI_DEFAULT);


    Transaction* current = NULL;
    Transaction* tail = NULL;



    while ((status = OCIStmtFetch2(stmthp, errhp, 1, OCI_DEFAULT, 0, OCI_DEFAULT)) == OCI_SUCCESS || status == OCI_SUCCESS_WITH_INFO) {

        Transaction* new_node = (Transaction*)malloc(sizeof(Transaction));
        strcpy(new_node->name, customer_name);
        strcpy(new_node->ticker, stock_name);
        //strcpy(new_node->type, trade_type);
        printf("종류: %s\n", trade_type);
        new_node->type = strcmp(trade_type, "SELL") ? 1 : 0;
        new_node->quantity = quantity;
        new_node->price = price;
        new_node->next = NULL;

        if (transactions == NULL) {
            transactions = new_node;
            tail = new_node;
        }
        else {
            tail->next = new_node;
            tail = new_node;
        }
    }

    // 세션 종료
    OCILogoff(svchp, errhp);
    OCIHandleFree((dvoid*)usrhp, OCI_HTYPE_SESSION);
    OCIHandleFree((dvoid*)svchp, OCI_HTYPE_SVCCTX);
    OCIHandleFree((dvoid*)srvhp, OCI_HTYPE_SERVER);
    OCIHandleFree((dvoid*)errhp, OCI_HTYPE_ERROR);
    OCIHandleFree((dvoid*)envhp, OCI_HTYPE_ENV);
}

void update_db_data() {
    OCIEnv* envhp;
    OCIError* errhp;
    OCISvcCtx* svchp;
    OCISession* usrhp;
    OCIServer* srvhp;
    OCIStmt* stmthp;
    OCIDefine* def1 = NULL, * def2 = NULL, * def3 = NULL, * def4 = NULL, * def5 = NULL;
    sword status;

    OCIParam* param;
    char* username = "C##2";
    char* password = "1234"; //오라클 비밀번호는 각자 것으로 쓰세요.
    char* dbname = "localhost:1521/xe";
    // 환경 핸들 생성
    if (OCIEnvCreate(&envhp, OCI_DEFAULT, NULL, NULL, NULL, NULL, 0, NULL) !=
        OCI_SUCCESS) {
        printf("OCIEnvCreate failed\n");
        return -1;
    }
    // 오류 핸들 생성
    if (OCIHandleAlloc((dvoid*)envhp, (dvoid**)&errhp, OCI_HTYPE_ERROR, (size_t)0,
        (dvoid**)NULL) != OCI_SUCCESS) {
        printf("OCIHandleAlloc failed for error handle\n");
        return -1;
    }
    // 서버 핸들 생성
    if (OCIHandleAlloc((dvoid*)envhp, (dvoid**)&srvhp, OCI_HTYPE_SERVER, (size_t)0,
        (dvoid**)NULL) != OCI_SUCCESS) {
        printf("OCIHandleAlloc failed for server handle\n");
        return -1;
    }
    // 서버 연결
    if (OCIServerAttach(srvhp, errhp, (text*)dbname, strlen(dbname), OCI_DEFAULT) !=
        OCI_SUCCESS) {
        check_error(errhp);
        return -1;
    }
    // 서비스 컨텍스트 생성
    if (OCIHandleAlloc((dvoid*)envhp, (dvoid**)&svchp, OCI_HTYPE_SVCCTX, (size_t)0,
        (dvoid**)NULL) != OCI_SUCCESS) {
        check_error(errhp);
        return -1;
    }
    // 세션 핸들 생성 및 연결
    if (OCIHandleAlloc((dvoid*)envhp, (dvoid**)&usrhp, OCI_HTYPE_SESSION, (size_t)0,
        (dvoid**)NULL) != OCI_SUCCESS) {
        check_error(errhp);
        return -1;
    }
    if (OCILogon2(envhp, errhp, &svchp,
        (OraText*)username, (ub4)strlen(username),
        (OraText*)password, (ub4)strlen(password),
        (OraText*)dbname, (ub4)strlen(dbname),
        OCI_DEFAULT /* 마지막 인수 추가 */
    ) != OCI_SUCCESS) {
        check_error(errhp);
        return -1;
    }

    //printf("Oracle Database connected successfully.\n");

    // 1. TABLE 초기화
    char* truncate_sql = "TRUNCATE TABLE TRANSACTIONS";
    OCIHandleAlloc(envhp, (void**)&stmthp, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmthp, errhp, (text*)truncate_sql, strlen(truncate_sql), OCI_NTV_SYNTAX, OCI_DEFAULT);

    status = OCIStmtExecute(svchp, stmthp, errhp, 1, 0, NULL, NULL, OCI_DEFAULT);
    if (status != OCI_SUCCESS) {
        check_error(errhp);
        exit(1);
    }
    printf("<테이블 TRUNCATE 성공>\n");

    // transactions 전역변수에 저장된 데이터를 DB에 삽입
    char* insert_sql = "INSERT INTO TRANSACTIONS (customer_name, stock_name, trade_type, quantity, price) VALUES (:1, :2, :3, :4, :5)";
    OCIHandleAlloc(envhp, (void**)&stmthp, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmthp, errhp, (text*)insert_sql, strlen(insert_sql), OCI_NTV_SYNTAX, OCI_DEFAULT);

    Transaction* current = transactions;
    while (current != NULL) {
        char customer_name[50];
        char stock_name[50];
        char type[20];
        int quantity;
        double price;

        // Transaction 구조체의 데이터를 변수에 복사
        strcpy(customer_name, current->name);
        strcpy(stock_name, current->ticker);
        current->type == 1 ? strcpy(type, "SELL") : strcpy(type, "BUY");
        quantity = current->quantity;
        price = current->price;

        OCIBind* bnd1 = NULL, * bnd2 = NULL, * bnd3 = NULL, * bnd4 = NULL, * bnd5 = NULL;
        OCIBindByPos(stmthp, &bnd1, errhp, 1, customer_name, sizeof(customer_name), SQLT_STR, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
        OCIBindByPos(stmthp, &bnd2, errhp, 2, stock_name, sizeof(stock_name), SQLT_STR, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
        OCIBindByPos(stmthp, &bnd3, errhp, 3, type, sizeof(type), SQLT_STR, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
        OCIBindByPos(stmthp, &bnd4, errhp, 4, &quantity, sizeof(quantity), SQLT_INT, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
        OCIBindByPos(stmthp, &bnd5, errhp, 5, &price, sizeof(price), SQLT_FLT, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);


        if (OCIStmtExecute(svchp, stmthp, errhp, 1, 0, NULL, NULL, OCI_COMMIT_ON_SUCCESS) != OCI_SUCCESS) {
            check_error(errhp);
        }
        else {
            printf("<데이터 삽입 완료>\n");
        }

        current = current->next; // 다음 노드로 이동
    }

    // 세션 종료
    OCILogoff(svchp, errhp);
    OCIHandleFree((dvoid*)usrhp, OCI_HTYPE_SESSION);
    OCIHandleFree((dvoid*)svchp, OCI_HTYPE_SVCCTX);
    OCIHandleFree((dvoid*)srvhp, OCI_HTYPE_SERVER);
    OCIHandleFree((dvoid*)errhp, OCI_HTYPE_ERROR);
    OCIHandleFree((dvoid*)envhp, OCI_HTYPE_ENV);
}
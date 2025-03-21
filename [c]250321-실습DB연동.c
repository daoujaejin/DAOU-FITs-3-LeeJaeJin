#include <stdio.h>
#include <oci.h>

void check_error(OCIError* errhp) {
    text errbuf[512];
    sb4 errcode = 0;
    OCIErrorGet((dvoid*)errhp, (ub4)1, (text*)NULL, &errcode, errbuf, (ub4)sizeof(errbuf),
        OCI_HTYPE_ERROR);
    printf("Error: %s\n", errbuf);
}

int main() {
    OCIEnv* envhp;
    OCIError* errhp;
    OCISvcCtx* svchp;
    OCISession* usrhp;
    OCIServer* srvhp;
    OCIStmt* stmthp;
    OCIDefine* def1 = NULL, * def2 = NULL;
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
    printf("Oracle Database connected successfully.\n");
    // -------------------------------------------------------------------------
    // 1. SELECT 문 실행
    char* select_sql = "SELECT ID, NAME FROM test_table";
    OCIHandleAlloc(envhp, (void**)&stmthp, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmthp, errhp, (text*)select_sql, strlen(select_sql), OCI_NTV_SYNTAX,
        OCI_DEFAULT);
    // 2. 쿼리 실행
    OCIStmtExecute(svchp, stmthp, errhp, 0, 0, NULL, NULL, OCI_DEFAULT);
    // 3. 데이터 바인딩 (결과를 받을 변수)
    int id;
    char name[50];
    OCIDefineByPos(stmthp, &def1, errhp, 1, &id, sizeof(id), SQLT_INT, NULL, NULL,
        NULL, OCI_DEFAULT); // <-- 여기 코드 추가 ⓑ
    OCIDefineByPos(stmthp, &def2, errhp, 2, name, sizeof(name), SQLT_STR, NULL,
        NULL, NULL, OCI_DEFAULT); // <-- 여기 코드 추가 ⓑ
    printf("<테이블 조회 결과>\n"); // <---여기 코드 추가 ⓒ
    printf("-----------------------------\n");
    printf("|  ID  |        NAME        |\n");
    printf("-----------------------------\n");
    // 4. 데이터 가져오기 <---여기 코드 추가 ⓓ
    while ((status = OCIStmtFetch2(stmthp, errhp, 1, OCI_DEFAULT, 0, OCI_DEFAULT))
        == OCI_SUCCESS || status == OCI_SUCCESS_WITH_INFO) {
        printf("| %4d | %-18s |\n", id, name); //<-- 여기 코드 추가  조회 후 출력하기 
    }
    printf("-----------------------------\n");

    // -------------------------------------------------------------------------
    /*** 데이터 삽입 (INSERT) ***/
    char* insert_sql = "INSERT INTO test_table (ID, NAME) VALUES (:1, :2)";
    OCIHandleAlloc(envhp, (void**)&stmthp, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmthp, errhp, (text*)insert_sql, strlen(insert_sql),
        OCI_NTV_SYNTAX, OCI_DEFAULT);
    int insert_id = 2;
    char insert_name[50] = "hyun";
    OCIBind* bnd1 = NULL, * bnd2 = NULL;
    OCIBindByPos(stmthp, &bnd1, errhp, 1, &insert_id, sizeof(insert_id), SQLT_INT,
        NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmthp, &bnd2, errhp, 2, insert_name, sizeof(insert_name),
        SQLT_STR, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);

    if (OCIStmtExecute(svchp, stmthp, errhp, 1, 0, NULL, NULL,
        OCI_COMMIT_ON_SUCCESS) != OCI_SUCCESS) {
        check_error(errhp);
    }
    else {
        printf("<데이터 삽입 완료>\n");
    }

    /*** 데이터 조회 (SELECT) ***/
    select_sql = "SELECT ID, NAME FROM test_table";
    OCIStmtPrepare(stmthp, errhp, (text*)select_sql, strlen(select_sql),
        OCI_NTV_SYNTAX, OCI_DEFAULT);
    OCIStmtExecute(svchp, stmthp, errhp, 0, 0, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def1, errhp, 1, &id, sizeof(id), SQLT_INT, NULL, NULL,
        NULL, OCI_DEFAULT); // <-- 여기 코드 추가 ⓑ
    OCIDefineByPos(stmthp, &def2, errhp, 2, name, sizeof(name), SQLT_STR, NULL,
        NULL, NULL, OCI_DEFAULT); // <-- 여기 코드 추가 ⓑ
    printf("<테이블 조회 결과>\n"); // <---여기 코드 추가 ⓒ
    printf("-----------------------------\n");
    printf("|  ID  |        NAME        |\n");
    printf("-----------------------------\n");
    // 4. 데이터 가져오기 <---여기 코드 추가 ⓓ
    while ((status = OCIStmtFetch2(stmthp, errhp, 1, OCI_DEFAULT, 0, OCI_DEFAULT))
        == OCI_SUCCESS || status == OCI_SUCCESS_WITH_INFO) {
        printf("| %4d | %-18s |\n", id, name); //<-- 여기 코드 추가  조회 후 출력하기 
    }
    printf("-----------------------------\n");
    // -------------------------------------------------------------------------

    /*** 데이터 삭제 (DELETE) ***/
    char* delete_sql = "DELETE FROM test_table WHERE ID = :1"; // ID를 기준으로 삭제
    OCIHandleAlloc(envhp, (void**)&stmthp, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmthp, errhp, (text*)delete_sql, strlen(delete_sql), OCI_NTV_SYNTAX,
        OCI_DEFAULT);
    int delete_id = 2;  // 삭제할 ID 값 (예: 2번 ID)

    // 바인딩 변수 설정 (DELETE)
    OCIBindByPos(stmthp, &bnd1, errhp, 1, &delete_id, sizeof(delete_id), SQLT_INT,
        NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);

    // SQL 실행
    if (OCIStmtExecute(svchp, stmthp, errhp, 1, 0, NULL, NULL,
        OCI_COMMIT_ON_SUCCESS) != OCI_SUCCESS) {
        check_error(errhp);
    }
    else {
        printf("<데이터 삭제 완료>\n");
    }

    /*** 데이터 조회 (SELECT) ***/
    select_sql = "SELECT ID, NAME FROM test_table";
    OCIStmtPrepare(stmthp, errhp, (text*)select_sql, strlen(select_sql), OCI_NTV_SYNTAX,
        OCI_DEFAULT);
    OCIStmtExecute(svchp, stmthp, errhp, 0, 0, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def1, errhp, 1, &id, sizeof(id), SQLT_INT, NULL, NULL,
        NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def2, errhp, 2, name, sizeof(name), SQLT_STR, NULL,
        NULL, NULL, OCI_DEFAULT);
    printf("<테이블 조회 결과>\n");
    printf("-----------------------------\n");
    printf("|  ID  |        NAME        |\n");
    printf("-----------------------------\n");
    while ((status = OCIStmtFetch2(stmthp, errhp, 1, OCI_DEFAULT, 0, OCI_DEFAULT))
        == OCI_SUCCESS || status == OCI_SUCCESS_WITH_INFO) {
        printf("| %4d | %-18s |\n", id, name);
    }
    printf("-----------------------------\n");



    // 세션 종료
    OCILogoff(svchp, errhp);
    OCIHandleFree((dvoid*)usrhp, OCI_HTYPE_SESSION);
    OCIHandleFree((dvoid*)svchp, OCI_HTYPE_SVCCTX);
    OCIHandleFree((dvoid*)srvhp, OCI_HTYPE_SERVER);
    OCIHandleFree((dvoid*)errhp, OCI_HTYPE_ERROR);
    OCIHandleFree((dvoid*)envhp, OCI_HTYPE_ENV);
    return 0;
}

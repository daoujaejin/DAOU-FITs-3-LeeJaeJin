#include "module.h"

Transaction* transactions = NULL;

// 기본 UI
int ask_choice() {
    int choice;

    printf("\n===== 주식 거래 시스템 =====\n");
    printf("1. 거래 추가\n");
    printf("2. 모든 거래 조회\n");
    printf("3. 고객 거래 검색\n");
    printf("4. 거래 수정\n");
    printf("5. 거래 삭제\n");
    printf("6. 종료\n");
    printf("선택: ");
    scanf("%d", &choice);
    switch (choice) {
    case 1:
        add_tr();
        return 0;
    case 2:
        print_all_tr();
        return 0;
    case 3: {
        print_cs_tr();
        return 0;
    }
    case 4: {
        modify_tr();
        return 0;
    }
    case 5: {
        delete_tr();
        return 0;
    }
    case 6:
        save_csv();
        return 1;
    default:
        printf("잘못된 선택\n");
        return 0;
    }
}

// (1) 거래 추가
void add_tr() {
	Transaction* tr = (Transaction*)malloc(sizeof(Transaction));

    printf("고객 이름: ");
    scanf("%s", tr->name);
    printf("주식 종목명: ");
    scanf("%s", tr->ticker);
    printf("거래 유형 (매수/매도): ");
    scanf("%d", &tr->type);
    printf("거래 수량: ");
    scanf("%d", &tr->quantity);
    printf("가격: ");
    scanf("%lf", &tr->price);
    tr->next = NULL;

    if (transactions == NULL) {
        transactions = tr;
    }
    else {
        Transaction* current = transactions;
        while (current->next) {
            current = current->next;
        }
        current->next = tr;
    }
}

// (2) 모든 거래 조회
void print_all_tr() {
    Transaction* now = transactions;

    printf("[모든 거래 내역]\n");
    if (transactions == NULL) {
        printf("거래가 없습니다.\n");
        return;
    }
    int i = 1;
    while (now) {
        printf("i = %d\n", i);
        printf("ID: %d, 고객: %s, 종목: %s, 유형: %s, 수량: %d, 가격: %.2f\n",
            i++, now->name, now->ticker, now->type ? "매도" : "매수", now->quantity, now->price);
        now = now->next;
    }
}

// (3) 특정 거래 조회
void print_cs_tr() {
    Transaction* now = transactions;
    char name[20];

    printf("검색할 고객 이름: ");
    scanf("%s", name);
    printf("[%s 고객 거래 내역]\n", name);
    int i = 1;
    while (now) {
        if (strcmp(name, now->name) == 0)
            printf("ID: %d, 고객: %s, 종목: %s, 유형: %s, 수량: %d, 가격: %.2f\n",
                i, now->name, now->ticker, now->type ? "매도" : "매수", now->quantity, now->price);
        now = now->next;
        i++;
    }
}

// (4) 거래 수정
void modify_tr() {
    int id;
    if (transactions == NULL) {
        printf("수정할 거래가 없습니다.\n");
        return;
    }
    Transaction *now = transactions;
    printf("수정할 거래 ID 입력: ");
    scanf("%d", &id);
    for (int i = 1; i < id; i++) {
        now = now->next;
        if (now == NULL) {
            printf("해당 ID를 찾을 수 없습니다.\n");
            return;
        }
    }

    printf("새 수량: ");
    scanf("%d", &now->quantity);
    printf("새 가격: ");
    scanf("%lf", &now->price);
}

// (5) 거래 삭제
void delete_tr() {
    int id;
    Transaction* now = transactions;
    Transaction* bef = transactions;
    if (now == NULL) {
        printf("삭제할 거래가 없습니다.\n");
        return;
    }
    printf("삭제할 거래 ID 입력: ");
    scanf("%d", &id);
    for (int i = 1; i < id; i++) {
        bef = now;
        now = now->next;
        if (now == NULL) {
            printf("해당 ID를 찾을 수 없습니다.\n");
            return;
        }
    }

    // 하나 남았을 때
    if (now->next == NULL) {
        if (now == bef) {
            transactions = NULL;
            free(now);
        }
        else {
            free(now);
            transactions = bef;
            bef->next = NULL;
        }
        return;
    }

    if ((now->next)->next == NULL) {
        free(now->next);
        now->next = NULL;
    }
    else {
        Transaction* temp = now->next->next;
        free(now->next);
        now->next = temp;
    }
    printf("거래 삭제 완료!\n");
}

// csv파일로 저장
void save_csv() {
    FILE* f = fopen("stocks.csv", "w");
    fprintf(f, "거래ID,고객이름,주식명,거래유형,거래량,가격\n");
    Transaction* now = transactions;
    int i = 1;
    while (now) {
        fprintf(f, "%d,%s,%s,%s,%d,%.2f\n",
            i++,
            now->name,
            now->ticker,
            now->type ? "매도" : "매수",
            now->quantity,
            now->price
        );
        now = now->next;
    }
    fclose(f);
}

// 데이터 할당 해제
void free_transactions() {
    if (transactions == NULL)
        return;
    Transaction* now = transactions;
    while (now) {
        Transaction* temp = now->next;
        free(now);
        now = temp;
    }
}

void save_binary() {
    FILE* f = fopen("stocks.dat", "wb");
    if (f == NULL) {
        printf("파일 열기 실패\n");
        return;
    }

    Transaction* current = transactions;
    while (current != NULL) {
        // 바이너리 파일에 데이터 쓰기
        fwrite(current->name, sizeof(current->name), 1, f);
        fwrite(current->ticker, sizeof(current->ticker), 1, f);
        fwrite(&current->type, sizeof(current->type), 1, f);
        fwrite(&current->quantity, sizeof(current->quantity), 1, f);
        fwrite(&current->price, sizeof(current->price), 1, f);

        current = current->next;
    }

    fclose(f);
    printf("바이너리 파일 저장 완료\n");
}

void check_error(OCIError* errhp) {
    text errbuf[512];
    sb4 errcode = 0;
    OCIErrorGet((dvoid*)errhp, (ub4)1, (text*)NULL, &errcode, errbuf, (ub4)sizeof(errbuf),
        OCI_HTYPE_ERROR);
    printf("Error: %s\n", errbuf);
}
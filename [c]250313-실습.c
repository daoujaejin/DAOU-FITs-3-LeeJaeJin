void pr_91_1_1() {
    int *arr[5];
    int ar0[4] = {0, 1, 2, 3};
    int ar1[4] = {10, 11, 12, 13};
    int ar2[4] = {20, 21, 22, 23};

    arr[0] = ar0;
    arr[1] = ar1;
    arr[2] = ar2;

    int choice;
    printf("삭제하려는 행의 인덱스 번호를 입력하세요: ");
    scanf_s("%d", &choice);
    arr[choice] = NULL;
    for (int i = 0; i < 3; i++) {
        if (arr[i] != NULL) {
            for (int j = 0; j < 4; j++)
                printf("%d ", arr[i][j]);
            printf("\n");
        }
    }
}

void get_sum(int **arr) {
    int sum = 0;

    for (int i = 0; i < 3; i++)
        for (int j = 0; j < 3; j++)
            sum += arr[i][j];
    printf("배열의 합은 %d\n", sum);
}

void get_max(int **arr) {
    int max = -1;    

    for (int i = 0; i < 3; i++)
        for (int j = 0; j < 3; j++) 
            if (max < arr[i][j])
                max = arr[i][j];
    printf("배열의 최대값은 %d\n", max);
}

void get_min(int **arr) {
    int min = 10;    

    for (int i = 0; i < 3; i++)
        for (int j = 0; j < 3; j++) 
            if (min > arr[i][j])
                min = arr[i][j];
    printf("배열의 최소값은 %d\n", min);
    
}

void get_square(int **arr) {
    printf("배열의 제곱값은\n");
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            printf("%d ", arr[i][j]);
        }
        printf("\n");
    }    
}

void pr_91_2_1() {
    void (*funcs[4])(int**) = {get_sum, get_max, get_min, get_square};
    int array[3][3] = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8}
    };
    int *ptrs[3];
    ptrs[0] = array[0];
    ptrs[1] = array[1];
    ptrs[2] = array[2];
    int choice;
    printf("연산 방법을 선택하기\n");
    printf("(0: 합, 1: 최대값, 2: 최소값, 3: 제곱): ");
    scanf_s("%d", &choice);
    funcs[choice](ptrs);
}

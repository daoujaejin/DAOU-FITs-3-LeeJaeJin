#include <stdio.h>
typedef struct student {
    char name[20];
    char sex;
    int stid;
    int sub1;
    int sub2;
    int sub3;
    double avg;
} ST;

int input(ST *st) {
    printf("%s 학생의 3과목 성적을 입력하세요: ", st->name);
    scanf("%d %d %d", &st->sub1, &st->sub2, &st->sub3);
    printf("input() 함수에서 입력된 값은 %d, %d, %d입니다.\n", st->sub1, st->sub2, st->sub3);
}

int main() {
    int i;
    ST st[3] = {{"kdhong", 'm', 1508001, 0, 0, 0, 0.0}, \
                {"jhkim", 'f', 1608001, 0, 0, 0, 0.0}, \
                {"cskim", 'm', 1708001, 0, 0, 0, 0.0}};
    for (i = 0; i < 3; i++) {
        input(&st[i]);
        printf("main()함수에서 출력된 세 과목의 점수는 %d, %d, %d입니다.\n", st[i].sub1, st[i].sub2, st[i].sub3);
    }
    return 0;
}

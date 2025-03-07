#include <stdio.h>

void one(float a, float b);
void two(int n);
void three(char *s);

int main(void) {
    // 1. 함수를 이용한 계산기 프로그램 작성
    float a, b;
    printf("가감승제를 운하는 두 수를 입력하세요: ");
    scanf_s("%f %f", &a, &b);
    one(a, b);

    // 2. 함수를 이용한 구구단 프로그램 작성
    int n;
    printf("원하는 구구단의 단을 입력하세요: ");
    scanf_s("%d", &n);
    two(n);

    // 3. 함수를 이용한 대소문자 변환 프로그램 작성
    char s[20];
    printf("문자열 입력: ");
    scanf_s("%s", s, 20);
    three(s);
}

void one(float a, float b) {
    printf("%.1f + %.1f = %.1f\n", a, b, a + b);
    printf("%.1f - %.1f = %.1f\n", a, b, a - b);
    printf("%.1f * %.1f = %.1f\n", a, b, a * b);
    printf("%.1f / %.1f = %.1f\n", a, b, a / b);
}

void two(int n) {
    printf("<<%d단>>\n", n);
    for (int i = 1; i <= 9; i++)
        printf("%d x %d = %d\n", n, i, n * i);
}

void three(char *s) {
    char res[20];
    for (int i = 0; i < 20; i++) {
        if (65 <= s[i] && s[i] <= 90) {
            res[i] = s[i] + 32;
        } else if (97 <= s[i] && s[i] <= 122) {
            res[i] = s[i] - 32;
        } else 
            res[i] = s[i];
    }
    printf("변환된 결과: %s\n", res);
}

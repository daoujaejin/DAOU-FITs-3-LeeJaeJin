#include <stdio.h>

int main()
{
    // 실습 6-1) 입력과 출력
    char gender;
    int age;
    float height;
    printf("성별은? (M / F) : ");
    scanf_s("%c", &gender, 1);
    getchar();
    printf("나이는? : ");
    scanf_s("%d", &age);
    getchar();
    printf("키는? : ");
    scanf_s("%f", &height);
    getchar();
    printf("================\n");
    printf("성별: %c\n", gender);
    printf("나이: %3d\n", age);
    printf("키: %.1f\n", height);
    printf("================\n");

    // 실습 6-2) 문자열
    char name[10]; // 배열은 같은 자료형을 연속 기억공간에 저장한 것
    printf("이름은? : ");
    fgets(name, 10, stdin);
    puts(name);
    scanf_s("%s", name, 10);
    printf("%s", name);

    // getchar()
    char gender;
    printf("성별은? (M / F) : ");
    gender = getchar();
    printf("성별 = %c\n", gender);

    // 실습 6-3) 여러 개의 자료 입력
    int i1, i2, i3;
    printf("세 개의 숫자를 입력하세요: ");
    scanf_s("%d %d %d", &i1, &i2, &i3);
    printf("결과: %d, %d, %d\n", i1, i2, i3);

    return 0;
}

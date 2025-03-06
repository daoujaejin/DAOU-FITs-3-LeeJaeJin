// 실습 7 입력과 출력
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

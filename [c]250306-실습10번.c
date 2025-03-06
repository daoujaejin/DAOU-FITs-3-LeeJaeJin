#include <stdio.h>

int pr10() {
	// 실습 10-1번
	printf("=========================\n");
	printf("== 1. 원의 둘레 구하기 ==\n");
	printf("== 2. 원의 넓이 구하기 ==\n");
	printf("== 3. 구의 부피 구하기 ==\n");
	printf("== 4. 그만두기         ==\n");
	printf("=========================\n");

	int answer, r;
printf("@원하는 내용은?");
scanf_s("%d", &answer);
switch (answer) {
	case 1:
		printf(">> 반지름은?");
		scanf_s("%d", &r);
		printf(">> 반지름이 %d인 원의 둘레는 %f\n", r, 2.0 * 3.14 * r);
		printf(">> 결과를 확인했으면 아무키나 누르세요\n\n");
		break;
	case 2:
		printf(">> 반지름은?");
		scanf_s("%d", &r);
		printf(">> 반지름이 %d인 원의 넓이는 %f\n", r, 3.14 * r * r);
		printf(">> 결과를 확인했으면 아무키나 누르세요\n\n");
		break;
	case 3:
		printf(">> 반지름은?");
		scanf_s("%d", &r);
		printf(">> 반지름이 %d인 구의 부피는 %f\n", r, 4.0 / 3 * 3.14 * r * r * r);
		printf(">> 결과를 확인했으면 아무키나 누르세요\n\n");
		break;
	case 4:
		printf(">> 아무키나 누르세요\n\n");
}

	// 실습 10-2번
	int n, sum = 0;
	printf("정수 n을 입력: ");
	scanf_s("%d", &n);
	for (int i = 1; i <= n; i++)
		if (i % 2 == 0)
			sum += i;
	printf("정수 1에서 %d 이하 짝수들의 합은 %d입니다.\n", n, sum);
}

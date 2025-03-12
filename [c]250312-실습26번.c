#include <stdio.h>

int main() {
    int sum = 10, score[] = {99, 80, 91, 78, 85};
    int *psum = &sum;
    int *pscore1 = score;
    int *pscore2 = score;
    ++pscore2;
    pscore2++;
    printf("변수 sum의 값: %d\n", sum);
    printf("포인터 변수 *psum의 값: %d\n", *psum);
    printf("포인터 변수 *psum+1의 값: %d\n", *psum+1);
    printf("포인터 변수 ++*psum의 값: %d\n", ++*psum);
    printf("포인터 변수 *(psum+1)의 값: %d\n", *(psum+1));
    printf("포인터 변수 *++psum의 값: %d\n", *++psum);
    printf("====================================\n");
    printf("포인터 변수 *pscore1의 값: %d\n", *pscore1);
    printf("포인터 변수 *pscore1+1의 값: %d\n", *pscore1+1);
    printf("포인터 변수 *(pscore1+1)의 값: %d\n", *(pscore1+1));
    printf("포인터 변수 *pscore2-*pscore1의 값: %d\n", *pscore2-*pscore1);
    printf("포인터 변수 pscore2-pscore1의 값: %d\n", pscore2-pscore1);
    printf("포인터 변수 pscore1의 값: %d\n", pscore1);
    printf("포인터 변수 pscore2의 값: %d\n", pscore2);
}

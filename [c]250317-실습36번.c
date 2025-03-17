#include <stdio.h>

int main() {
    union year {
        int yaer;
        int grade;
    };
    typedef struct {
        char name[20];
        char sex;
        int stid;
        union year y;
    } ST;

    ST st1 = {"kdhong", 'm', 1508001};
    st1.y.grade = 4;
    printf("%s 학생은 %d학년입니다\n", st1.name, st1.y);
    printf("%s 학생은 %d학년입니다\n", st1.name, st1.y.grade);
    printf("%s 학생은 %d학년입니다\n", st1.name, st1.y.yaer);
    st1.y.yaer = 1;
    printf("%s 학생은 %d학년입니다\n", st1.name, st1.y);
    printf("%s 학생은 %d학년입니다\n", st1.name, st1.y.grade);
    printf("%s 학생은 %d학년입니다\n", st1.name, st1.y.yaer);
}

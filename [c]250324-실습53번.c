#include <stdio.h>

#define ONE

#ifndef !ONE
  int a = 1;
#else
  int a = 2;
#endif

int main() {
  printf("a:%d",a);
}

#pragma once
#ifndef MODULE_H
#define MODULE_H

#define _CRT_SECURE_NO_WARNINGS

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <oci.h>
#include <time.h>

#define TYPE_BUY	0;
#define TYPE_SELL	1;

// 고객의 이름, 주식 종목명, 거래 유형 (매수/매도), 거래 수량, 가격을 입력받아 저장
typedef struct s_Transaction{
	char	name[20];
	char	ticker[20];
	int		type;
	int		quantity;
	double	price;
	struct s_Transaction* next;
} Transaction;

extern Transaction* transactions;

// 기본 UI
int ask_choice();

// (1) 거래 추가
void add_tr();

// (2) 모든 거래 조회
void print_all_tr();

// (3) 특정 거래 조회
void print_cs_tr();

// (4) 거래 수정
void modify_tr();

// (5) 거래 삭제
void delete_tr();

// csv파일로 저장
void save_csv();

// 바이너리 파일 저장
void save_binary();

// 데이터 할당 해제
void free_transactions();

// 에러 체크
void check_error(OCIError* errhp);

#endif
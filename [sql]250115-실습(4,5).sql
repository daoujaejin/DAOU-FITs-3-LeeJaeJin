-- (4)고객 테이블에서 고객 이름을 오름차순 정렬하여 출력
SELECT NAME 
FROM CUSTOMERS
ORDER BY NAME;

-- (4)계좌 테이블에서 잔액이 높은 순으로 계좌 ID와 잔액을 출력
SELECT ACCOUNT_ID, BALANCE 
FROM ACCOUNTS
ORDER BY BALANCE DESC;

-- (4)거래 테이블에서 거래 금액이 낮은 순으로 거래 ID와 금액을 출력
SELECT TRANSACTION_ID, AMOUNT
FROM TRANSACTIONS
ORDER BY AMOUNT;

-- (4)대출 테이블에서 대출 금액이 높은 순으로 대출 ID와 고객 ID를 출력
SELECT LOAN_ID, CUSTOMER_ID
FROM LOANS
ORDER BY AMOUNT DESC;

-- (5)고객 테이블에서 이름을 오름차순, 이메일을 내림차순으로 정렬하여 출력
SELECT *
FROM CUSTOMERS
ORDER BY NAME, EMAIL DESC;

-- (5)계좌 테이블에서 잔액을 내림차순으로 정렬하되 동일 잔액일 경우 계좌 ID를 오름차순으로 정렬하여 출력
SELECT *
FROM ACCOUNTS
ORDER BY BALANCE DESC, ACCOUNT_ID;

-- (5)거래 테이블에서 거래 유형을 기준으로 오름차순, 거래 금액을 기준으로 내림차순으로 정렬하여 출력
SELECT *
FROM TRANSACTIONS
ORDER BY TRANSACTION_TYPE, AMOUNT DESC;

-- (5)대출 테이블에서 대출 상태를 기준으로 오름차순, 대출 금액윽 기준으로 내림차순으로 정렬하여 출력
SELECT *
FROM LOANS
ORDER BY STATUS, AMOUNT DESC;

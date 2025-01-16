-- (7)각 지점의 이름과 대출 신청 고객 이름, 대출 금액을 출력 (대출 신청이 없는 지점도 포함)
SELECT B.NAME, C.NAME, L.AMOUNT
FROM BRANCHES B
LEFT OUTER JOIN LOANS L
    ON B.BRANCH_ID = B.BRANCH_ID
LEFT OUTER JOIN CUSTOMERS C
    ON L.CUSTOMER_ID = C.CUSTOMER_ID;

-- (7)잔액이 50,000 이상인 계좌를 가진 고객의 이름과 이메일을 출력
SELECT C.NAME, C.EMAIL
FROM CUSTOMERS C
JOIN ACCOUNTS A
    ON C.CUSTOMER_ID = A.CUSTOMER_ID
WHERE A.BALANCE >= 50000;

-- (7)대출 상태가 PENDING인 대출을 신청한 고객 이름과 대출 금액을 조회
SELECT C.NAME, L.AMOUNT
FROM CUSTOMERS C
JOIN LOANS L
    ON C.CUSTOMER_ID = L.CUSTOMER_ID
WHERE L.STATUS = 'PENDING';

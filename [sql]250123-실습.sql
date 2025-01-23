-- 실습 1번
-- 지점별 승인된 대출의 총 금액이 전체 지점에서 승인된 대출 금액의 평균보다 낮은 지점
-- 지점명, 지점별 총 대출 금액, 전체 평균 출력
SELECT *
FROM (
    SELECT (SELECT NAME FROM BRANCHES WHERE L.BRANCH_ID = BRANCH_ID) 지점명, 
        SUM(AMOUNT) OVER (PARTITION BY BRANCH_ID) "지점별 총 대출 금액",
        ROUND(AVG(AMOUNT) OVER (), 2) "전체 대출 평균"
    FROM LOANS L
    WHERE STATUS = 'APPROVED'
    )
WHERE "지점별 총 대출 금액" < "전체 대출 평균";

-- 실습 2번
-- 모든 직원의 사번, 이름, 상사의 이름, 상사의 부서 출력
-- 없으면 'NO MANAGER', 'NO DEPARTMENT' 출력
SELECT 사번, 이름,
    NVL("상사의 이름", 'NO MANAGER') "상사 이름",
    NVL((SELECT DEPARTMENT_NAME FROM DEPARTMENTS WHERE DEPARTMENT_ID = "상사의 부서 ID"), 'NO DEPARTMENT') "상사 부서"
FROM (
    SELECT EMPLOYEE_ID 사번, NAME 이름, 
        (SELECT NAME FROM EMPLOYEES WHERE E1.MANAGER_ID = EMPLOYEE_ID) "상사의 이름",
        (SELECT DEPARTMENT_ID FROM EMPLOYEES WHERE E1.MANAGER_ID = EMPLOYEE_ID) "상사의 부서 ID"
    FROM EMPLOYEES E1);
-- -> SELF JOIN으로 해보기
SELECT E.EMPLOYEE_ID 사번,
    E.NAME 이름,
    NVL(M.NAME, 'NO MANAGER') "상사 이름",
    NVL(D.DEPARTMENT_NAME, 'NO DEPARTMENT') "상사 부서"
FROM EMPLOYEES E
    LEFT JOIN EMPLOYEES M ON E.MANAGER_ID = M.EMPLOYEE_ID
    LEFT JOIN DEPARTMENTS D ON M.DEPARTMENT_ID = D.DEPARTMENT_ID;

-- 실습 3번
-- 사번, 이름, 급여, 백분율 순서대로 출력
SELECT EMPLOYEE_ID 사번, SALARY 급여,
    ROUND(SALARY / SUM(SALARY) OVER () * 100, 2) 백분율
FROM EMPLOYEES
WHERE SALARY IS NOT NULL
ORDER BY 백분율 DESC;

-- 실습 4번
-- 각 계좌의 최고 거래 금액이 높은 순으로 상위 5위까지의 계좌 출력
SELECT * FROM (
    SELECT ACCOUNT_ID "계좌 ID", MAX(BALANCE) "최고 거래금액",
        DENSE_RANK() OVER (ORDER BY MAX(BALANCE) DESC) 순위
    FROM ACCOUNTS
    GROUP BY ACCOUNT_ID)
WHERE 순위 <= 5;

-- 실습 5번
-- 각 부서에서 급여가 가장 높은 직원과 가장 낮은 직원의 급여 차이를 계한하여 출력
SELECT DEPARTMENT_ID "부서 ID", MAX(SALARY) - MIN(SALARY) "최대, 최소 급여 차이"
FROM EMPLOYEES
WHERE DEPARTMENT_ID IS NOT NULL
GROUP BY DEPARTMENT_ID
ORDER BY "부서 ID";

-- 실습 6번
-- 사번, 이름, 급여, 부서 평균 급여, 차이를 출력
SELECT EMPLOYEE_ID 사번, NAME 이름, SALARY 급여, "부서별 평균 급여", SALARY - "부서별 평균 급여" "급여의 차이"
FROM EMPLOYEES E
    LEFT JOIN (
        SELECT DEPARTMENT_ID, ROUND(AVG(SALARY), 2) "부서별 평균 급여"
        FROM EMPLOYEES
        WHERE DEPARTMENT_ID IS NOT NULL
        GROUP BY DEPARTMENT_ID) T
    ON E.DEPARTMENT_ID = T.DEPARTMENT_ID;

SELECT EMPLOYEE_ID 사번, NAME 이름, SALARY 급여,
    ROUND(AVG(SALARY) OVER(PARTITION BY DEPARTMENT_ID), 2) "부서별 평균 급여",
    SALARY - ROUND(AVG(SALARY) OVER(PARTITION BY DEPARTMENT_ID), 2) "급여의 차이"
FROM EMPLOYEES;

-- 실습 7번
-- 이름이 'Employee 241'인 사우너의 모든 상사를 한 줄로 출력
SELECT *
FROM (
    SELECT LTRIM(SYS_CONNECT_BY_PATH(NAME, ' > '), ' > ') "모든 상사들"
    FROM EMPLOYEES
    START WITH NAME = 'Employee 241'
    CONNECT BY PRIOR MANAGER_ID = EMPLOYEE_ID
    ORDER BY LEVEL DESC)
WHERE ROWNUM = 1;
-- 강사님 쿼리문
SELECT MAX(LTRIM(SYS_CONNECT_BY_PATH(NAME, ' > '), ' > ')) "모든 상사들"
FROM EMPLOYEES
START WITH NAME = 'Employee 241'
CONNECT BY PRIOR MANAGER_ID = EMPLOYEE_ID;

-- 실습 8번
-- 급여가 높은 상위 10명의 사원 정보 출력
-- 급여가 동일한 경우 사원 이름 순으로 정렬
SELECT * FROM (
    SELECT *
    FROM EMPLOYEES
    WHERE SALARY IS NOT NULL
    ORDER BY SALARY DESC, NAME)
WHERE ROWNUM <= 10;

-- 실습 9번
-- 입사 연도별 급여 합계를 출력
-- 가장 아랫줄에 'ALL YEARS'로 전체 급여 합계 출력
SELECT NVL(TO_CHAR(HIRE_DATE, 'YYYY'), 'ALL YEARS') "입사 연도", SUM(SALARY) "급여 합계"
FROM EMPLOYEES
WHERE HIRE_DATE IS NOT NULL -- 만약 HIRE_DATE = NULL 인 데이터는 따로 뽑아야 하면 GROUPING 사용해서 구분하기
GROUP BY ROLLUP(TO_CHAR(HIRE_DATE, 'YYYY'))
ORDER BY "입사 연도";

-- 실습 10번
-- 부서별, 직무별, 전체 급여 합계를 출력
SELECT (SELECT DEPARTMENT_NAME FROM DEPARTMENTS WHERE E.DEPARTMENT_ID = DEPARTMENT_ID) 부서명, 
    JOB_ID "직무 ID", SUM(SALARY) "급여 합계"
FROM EMPLOYEES E
WHERE DEPARTMENT_ID IS NOT NULL
GROUP BY GROUPING SETS(DEPARTMENT_ID, JOB_ID, ())
ORDER BY DEPARTMENT_ID, JOB_ID;

-- 실습 11번
-- 부서 ID, 사원 이름, 급여, -100 ~ +100 급여 합계
SELECT DEPARTMENT_ID "부서 ID", NAME 사원이름, SALARY 급여,
    SUM(SALARY) OVER (PARTITION BY DEPARTMENT_ID
        ORDER BY SALARY
        RANGE BETWEEN 100 PRECEDING AND 100 FOLLOWING) "계산된 급여"
FROM EMPLOYEES
ORDER BY DEPARTMENT_ID;

-- 실습 12번
--  사번, 급여, 직무 평균과 비교, 전체 평균과 비교 출력
SELECT 사번, 급여, 
    CASE
        WHEN 급여 >= "직무 평균 급여" THEN '직무 평균 이상'
        ELSE '직무 평균 이하'
    END "직무 평균 급여 비교",
    CASE
        WHEN 급여 >= "전체 평균 급여" THEN '전체 평균 이상'
        ELSE '전체 평균 이하'
    END "전체 평균 급여 비교"
FROM (
    SELECT
        EMPLOYEE_ID 사번, 
        SALARY 급여,
        AVG(SALARY) OVER (PARTITION BY JOB_ID) "직무 평균 급여",
        AVG(SALARY) OVER () "전체 평균 급여"
    FROM EMPLOYEES);

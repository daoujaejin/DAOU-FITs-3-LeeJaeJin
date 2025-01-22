-- 실습12-1
-- 사원 테이블에서 부서별로 급여의 합계를 계산하고 전체 합계를 함께 출력
-- (부서데이터가 NULL인 사원은 제외)
SELECT CASE 
        WHEN GROUPING(DEPARTMENT_ID) = 1 THEN '전체 합계'
        ELSE (SELECT DEPARTMENT_NAME FROM DEPARTMENTS WHERE E.DEPARTMENT_ID = DEPARTMENT_ID)
    END 부서ID, SUM(SALARY) "부서 급여 합계"
FROM EMPLOYEES E
WHERE DEPARTMENT_ID IS NOT NULL
GROUP BY CUBE(DEPARTMENT_ID)
ORDER BY DEPARTMENT_ID;

-- 실습12-2
-- 같은 날짜에 입사한 가원이 2명 이상인 날짜와 입사한 사원수,
-- 마지막 행은 총 직원수를 함께 출력
SELECT NVL(TO_CHAR(HIRE_DATE), '총 직원수') 입사일, 
    COUNT(*) AS CNT
FROM EMPLOYEES
GROUP BY CUBE(HIRE_DATE)
HAVING COUNT(*) >= 2
ORDER BY HIRE_DATE;

-- 실습12-3
-- 각 지점별 대출 금액 합계와 모든 지점의 총합계를 출력
-- 출력 컬럼은 지점명, 대출 금액 합계, 지점 총합계는 'All Branch'로 출력
-- 지점명으로 정렬하고, 총합계는 맨 아랫줄에
SELECT CASE
        WHEN GROUPING(BRANCH_ID) = 1 THEN 'All Branch'
        ELSE (SELECT NAME FROM BRANCHES B WHERE B.BRANCH_ID = L.BRANCH_ID)
    END 지점명, 
    SUM(AMOUNT) "대출 금액 합계"
FROM LOANS L
GROUP BY CUBE(BRANCH_ID)
ORDER BY BRANCH_ID;

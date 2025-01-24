-- 튜닝

-- 예시
ALTER SESSION SET STATISTICS_LEVEL=ALL;

SELECT * FROM EMPLOYEES WHERE ROWNUM <= 100;
--------------------------------------------------------------------------------------------
--| Id  | Operation          | Name      | Starts | E-Rows | A-Rows |   A-Time   | Buffers |
--------------------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT   |           |      1 |        |    100 |00:00:00.01 |       5 |
--|*  1 |  COUNT STOPKEY     |           |      1 |        |    100 |00:00:00.01 |       5 |
--|   2 |   TABLE ACCESS FULL| EMPLOYEES |      1 |  16836 |    100 |00:00:00.01 |       5 |
--------------------------------------------------------------------------------------------

SELECT * FROM table(dbms_xplan.display_cursor(NULL, NULL, 'ALLSTATS LAST'));

-- 1번
ALTER SESSION SET STATISTICS_LEVEL=ALL;

SELECT * FROM employees
WHERE emp_no LIKE '101%'
    OR emp_no LIKE '103%';
-------------------------------------------------------------------------------------------
--| Id  | Operation         | Name      | Starts | E-Rows | A-Rows |   A-Time   | Buffers |
-------------------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT  |           |      1 |        |    200 |00:00:00.01 |     147 |
--|*  1 |  TABLE ACCESS FULL| EMPLOYEES |      1 |    252 |    200 |00:00:00.01 |     147 |
-------------------------------------------------------------------------------------------

SELECT * FROM employees
WHERE (emp_no >= 10100 AND emp_no < 10200)
    OR (emp_no >= 10300 AND emp_no < 10400);
---------------------------------------------------------------------------------------------------------------
--| Id  | Operation                           | Name        | Starts | E-Rows | A-Rows |   A-Time   | Buffers |
---------------------------------------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT                    |             |      1 |        |    200 |00:00:00.01 |      10 |
--|   1 |  TABLE ACCESS BY INDEX ROWID BATCHED| EMPLOYEES   |      1 |    252 |    200 |00:00:00.01 |      10 |
--|   2 |   BITMAP CONVERSION TO ROWIDS       |             |      1 |        |    200 |00:00:00.01 |       4 |
--|   3 |    BITMAP OR                        |             |      1 |        |      1 |00:00:00.01 |       4 |
--|   4 |     BITMAP CONVERSION FROM ROWIDS   |             |      1 |        |      1 |00:00:00.01 |       2 |
--|   5 |      SORT ORDER BY                  |             |      1 |        |    100 |00:00:00.01 |       2 |
--|*  6 |       INDEX RANGE SCAN              | SYS_C008408 |      1 |        |    100 |00:00:00.01 |       2 |
--|   7 |     BITMAP CONVERSION FROM ROWIDS   |             |      1 |        |      1 |00:00:00.01 |       2 |
--|   8 |      SORT ORDER BY                  |             |      1 |        |    100 |00:00:00.01 |       2 |
--|*  9 |       INDEX RANGE SCAN              | SYS_C008408 |      1 |        |    100 |00:00:00.01 |       2 |
---------------------------------------------------------------------------------------------------------------
    
SELECT * FROM table(dbms_xplan.display_cursor(NULL, NULL, 'ALLSTATS LAST'));

-- 2번
ALTER SESSION SET STATISTICS_LEVEL=ALL;

SELECT NVL(GENDER, 'NON') AS GENDER, COUNT(*)
FROM EMPLOYEES
GROUP BY NVL(GENDER, 'NON');
------------------------------------------------------------------------------------------------
--| Id  | Operation             | Name       | Starts | E-Rows | A-Rows |   A-Time   | Buffers |
------------------------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT      |            |      1 |        |      2 |00:00:00.01 |      43 |
--|   1 |  HASH GROUP BY        |            |      1 |  16836 |      2 |00:00:00.01 |      43 |
--|   2 |   INDEX FAST FULL SCAN| IDX_GENDER |      1 |  16836 |  20000 |00:00:00.01 |      43 |
------------------------------------------------------------------------------------------------

SELECT /*+INDEX(EMPLOYEES IDX_GENDER)*/ GENDER AS GENDER, COUNT(*)
FROM EMPLOYEES
GROUP BY GENDER;
-----------------------------------------------------------------------------------------------
--| Id  | Operation            | Name       | Starts | E-Rows | A-Rows |   A-Time   | Buffers |
-----------------------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT     |            |      1 |        |      2 |00:00:00.01 |      38 |
--|   1 |  SORT GROUP BY NOSORT|            |      1 |  16836 |      2 |00:00:00.01 |      38 |
--|   2 |   INDEX FULL SCAN    | IDX_GENDER |      1 |  16836 |  20000 |00:00:00.01 |      38 |
-----------------------------------------------------------------------------------------------

SELECT * FROM table(dbms_xplan.display_cursor(NULL, NULL, 'ALLSTATS LAST'));

-- 3번
ALTER SESSION SET STATISTICS_LEVEL=ALL;

SELECT * FROM EMPLOYEES
WHERE GENDER||' '|| LAST_NAME = 'M Radwan';
-------------------------------------------------------------------------------------------
--| Id  | Operation         | Name      | Starts | E-Rows | A-Rows |   A-Time   | Buffers |
-------------------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT  |           |      1 |        |      5 |00:00:00.01 |     143 |
--|*  1 |  TABLE ACCESS FULL| EMPLOYEES |      1 |     12 |      5 |00:00:00.01 |     143 |
-------------------------------------------------------------------------------------------

SELECT * 
FROM EMPLOYEES
WHERE LAST_NAME = 'Radwan' AND GENDER = 'M';
------------------------------------------------------------------------------------------------------------------------
--| Id  | Operation                           | Name                 | Starts | E-Rows | A-Rows |   A-Time   | Buffers |
------------------------------------------------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT                    |                      |      1 |        |      5 |00:00:00.01 |       7 |
--|   1 |  TABLE ACCESS BY INDEX ROWID BATCHED| EMPLOYEES            |      1 |     12 |      5 |00:00:00.01 |       7 |
--|*  2 |   INDEX RANGE SCAN                  | IDX_GENDER_LAST_NAME |      1 |     12 |      5 |00:00:00.01 |       2 |
------------------------------------------------------------------------------------------------------------------------

SELECT * FROM table(dbms_xplan.display_cursor(NULL, NULL, 'ALLSTATS LAST'));

-- 4번
ALTER SESSION SET STATISTICS_LEVEL=ALL;

SELECT FIRST_NAME, LAST_NAME, EMP_NO
FROM EMPLOYEES
WHERE SUBSTR(EMP_NO, 1, 4) = 1030;
-------------------------------------------------------------------------------------------
--| Id  | Operation         | Name      | Starts | E-Rows | A-Rows |   A-Time   | Buffers |
-------------------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT  |           |      1 |        |     10 |00:00:00.01 |     143 |
--|*  1 |  TABLE ACCESS FULL| EMPLOYEES |      1 |     39 |     10 |00:00:00.01 |     143 |
-------------------------------------------------------------------------------------------

SELECT FIRST_NAME, LAST_NAME, EMP_NO
FROM EMPLOYEES
WHERE EMP_NO >= 10300
    AND EMP_NO < 10310;
---------------------------------------------------------------------------------------------------------------
--| Id  | Operation                           | Name        | Starts | E-Rows | A-Rows |   A-Time   | Buffers |
---------------------------------------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT                    |             |      1 |        |     10 |00:00:00.01 |       3 |
--|   1 |  TABLE ACCESS BY INDEX ROWID BATCHED| EMPLOYEES   |      1 |     10 |     10 |00:00:00.01 |       3 |
--|*  2 |   INDEX RANGE SCAN                  | SYS_C008408 |      1 |     10 |     10 |00:00:00.01 |       2 |
---------------------------------------------------------------------------------------------------------------

SELECT * FROM table(dbms_xplan.display_cursor(NULL, NULL, 'ALLSTATS LAST'));

-- 5번
ALTER SESSION SET STATISTICS_LEVEL=ALL;

SELECT DISTINCT E.EMP_NO, E.FIRST_NAME, E.LAST_NAME, D.DEPT_NO
FROM EMPLOYEES E, DEPT_MANAGER D
WHERE E.EMP_NO = D.EMP_NO;
---------------------------------------------------------------------------------------------------------
--| Id  | Operation                     | Name        | Starts | E-Rows | A-Rows |   A-Time   | Buffers |
---------------------------------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT              |             |      1 |        |     24 |00:00:00.01 |      51 |
--|   1 |  HASH UNIQUE                  |             |      1 |     24 |     24 |00:00:00.01 |      51 |
--|   2 |   NESTED LOOPS                |             |      1 |     24 |     24 |00:00:00.01 |      51 |
--|   3 |    NESTED LOOPS               |             |      1 |     24 |     24 |00:00:00.01 |      27 |
--|   4 |     INDEX FAST FULL SCAN      | SYS_C008396 |      1 |     24 |     24 |00:00:00.01 |       4 |
--|*  5 |     INDEX UNIQUE SCAN         | SYS_C008408 |     24 |      1 |     24 |00:00:00.01 |      23 |
--|   6 |    TABLE ACCESS BY INDEX ROWID| EMPLOYEES   |     24 |      1 |     24 |00:00:00.01 |      24 |
---------------------------------------------------------------------------------------------------------

SELECT E.EMP_NO, E.FIRST_NAME, E.LAST_NAME, D.DEPT_NO
FROM EMPLOYEES E, DEPT_MANAGER D
WHERE E.EMP_NO = D.EMP_NO;
--------------------------------------------------------------------------------------------------------
--| Id  | Operation                    | Name        | Starts | E-Rows | A-Rows |   A-Time   | Buffers |
--------------------------------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT             |             |      1 |        |     24 |00:00:00.01 |      51 |
--|   1 |  NESTED LOOPS                |             |      1 |     24 |     24 |00:00:00.01 |      51 |
--|   2 |   NESTED LOOPS               |             |      1 |     24 |     24 |00:00:00.01 |      27 |
--|   3 |    INDEX FAST FULL SCAN      | SYS_C008396 |      1 |     24 |     24 |00:00:00.01 |       4 |
--|*  4 |    INDEX UNIQUE SCAN         | SYS_C008408 |     24 |      1 |     24 |00:00:00.01 |      23 |
--|   5 |   TABLE ACCESS BY INDEX ROWID| EMPLOYEES   |     24 |      1 |     24 |00:00:00.01 |      24 |
--------------------------------------------------------------------------------------------------------

SELECT * FROM table(dbms_xplan.display_cursor(NULL, NULL, 'ALLSTATS LAST'));

-- 6번
ALTER SESSION SET STATISTICS_LEVEL=ALL;

SELECT COUNT(DISTINCT E.EMP_NO) AS CNT
FROM EMPLOYEES E,
    (SELECT EMP_NO
    FROM SALARIES
    WHERE SALARY > 50000) S
WHERE E.EMP_NO = S.EMP_NO;
-------------------------------------------------------------------------------------------------------------------------------
--| Id  | Operation                | Name        | Starts | E-Rows | A-Rows |   A-Time   | Buffers |  OMem |  1Mem | Used-Mem |
-------------------------------------------------------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT         |             |      1 |        |      1 |00:00:00.02 |     934 |       |       |          |
--|   1 |  SORT AGGREGATE          |             |      1 |      1 |      1 |00:00:00.02 |     934 |       |       |          |
--|   2 |   VIEW                   | VM_NWVW_1   |      1 |   1427 |  17484 |00:00:00.02 |     934 |       |       |          |
--|   3 |    HASH GROUP BY         |             |      1 |   1427 |  17484 |00:00:00.02 |     934 |  2406K|  2406K| 2101K (0)|
--|*  4 |     HASH JOIN SEMI       |             |      1 |   1427 |  17484 |00:00:00.02 |     934 |  2801K|  2801K| 2181K (0)|
--|   5 |      INDEX FAST FULL SCAN| SYS_C008408 |      1 |  16836 |  20000 |00:00:00.01 |      55 |       |       |          |
--|*  6 |      TABLE ACCESS FULL   | SALARIES    |      1 |    139K|    144K|00:00:00.01 |     879 |       |       |          |
-------------------------------------------------------------------------------------------------------------------------------

SELECT COUNT(DISTINCT E.EMP_NO) AS CNT
FROM EMPLOYEES E,
    (SELECT EMP_NO
    FROM SALARIES
    WHERE SALARY > 50000) S
WHERE S.EMP_NO = E.EMP_NO;
-------------------------------------------------------------------------------------------------------------------------------
--| Id  | Operation                | Name        | Starts | E-Rows | A-Rows |   A-Time   | Buffers |  OMem |  1Mem | Used-Mem |
-------------------------------------------------------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT         |             |      1 |        |      1 |00:00:00.01 |     934 |       |       |          |
--|   1 |  SORT AGGREGATE          |             |      1 |      1 |      1 |00:00:00.01 |     934 |       |       |          |
--|   2 |   VIEW                   | VM_NWVW_1   |      1 |   1427 |  17484 |00:00:00.01 |     934 |       |       |          |
--|   3 |    HASH GROUP BY         |             |      1 |   1427 |  17484 |00:00:00.01 |     934 |  2406K|  2406K| 1934K (0)|
--|*  4 |     HASH JOIN SEMI       |             |      1 |   1427 |  17484 |00:00:00.01 |     934 |  2801K|  2801K| 2250K (0)|
--|   5 |      INDEX FAST FULL SCAN| SYS_C008408 |      1 |  16836 |  20000 |00:00:00.01 |      55 |       |       |          |
--|*  6 |      TABLE ACCESS FULL   | SALARIES    |      1 |    139K|    144K|00:00:00.01 |     879 |       |       |          |
-------------------------------------------------------------------------------------------------------------------------------

SELECT COUNT(E.EMP_NO) AS CNT
FROM EMPLOYEES E
WHERE EXISTS (SELECT 1
                FROM SALARIES S
                WHERE S.EMP_NO = E.EMP_NO
                AND S.SALARY > 50000);
-----------------------------------------------------------------------------------------------------------------------------
--| Id  | Operation              | Name        | Starts | E-Rows | A-Rows |   A-Time   | Buffers |  OMem |  1Mem | Used-Mem |
-----------------------------------------------------------------------------------------------------------------------------
--|   0 | SELECT STATEMENT       |             |      1 |        |      1 |00:00:00.03 |     934 |       |       |          |
--|   1 |  SORT AGGREGATE        |             |      1 |      1 |      1 |00:00:00.03 |     934 |       |       |          |
--|*  2 |   HASH JOIN SEMI       |             |      1 |   1427 |  17484 |00:00:00.03 |     934 |  2801K|  2801K| 2252K (0)|
--|   3 |    INDEX FAST FULL SCAN| SYS_C008408 |      1 |  16836 |  20000 |00:00:00.01 |      55 |       |       |          |
--|*  4 |    TABLE ACCESS FULL   | SALARIES    |      1 |    139K|    144K|00:00:00.01 |     879 |       |       |          |
-----------------------------------------------------------------------------------------------------------------------------

SELECT * FROM table(dbms_xplan.display_cursor(NULL, NULL, 'ALLSTATS LAST'));

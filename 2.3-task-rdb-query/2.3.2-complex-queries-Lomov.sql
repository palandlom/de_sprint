
-- a. Попробуйте вывести не просто самую высокую зарплату во всей команде, 
-- а вывести именно фамилию сотрудника с самой высокой зарплатой.

SELECT  "Surname", "SLevel"  AS "Max salary"  FROM public."Employee" ORDER BY "SLevel" DESC LIMIT 1

-- b. Попробуйте вывести фамилии сотрудников в алфавитном порядке

SELECT  "Surname" FROM public."Employee" ORDER BY "Surname" 

-- c. Рассчитайте средний стаж для каждого уровня сотрудников

SELECT  "QLevel", avg (AGE(CURRENT_DATE,"WorkStartDate")) AS "Mean Experience"  FROM public."Employee" GROUP BY "QLevel"

-- d. Выведите фамилию сотрудника и название отдела, в котором он работает

SELECT "Surname",dep."Name"  FROM "Employee" as emp JOIN "Department" as dep ON emp."DepartmentId"=dep."DepId"

-- e. Выведите название отдела и фамилию сотрудника с самой высокой зарплатой в данном отделе и саму зарплату также.

SELECT emp1."Surname",emp1."DepartmentId" as dpId1, maxSalaryInDep.maxsal  FROM "Employee" AS emp1 
JOIN 
    (SELECT "DepartmentId" , MAX("SLevel") As maxSal 
     FROM "Employee" as emp 
     JOIN "Department" as dep ON emp."DepartmentId"=dep."DepId"
     GROUP BY "DepartmentId" 
    ) AS maxSalaryInDep
ON emp1."DepartmentId"  = maxSalaryInDep."DepartmentId" AND  emp1."SLevel"= maxSal






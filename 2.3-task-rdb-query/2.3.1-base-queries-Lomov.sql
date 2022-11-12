-- -- -- -- -- 
-- Запросы
-- -- -- -- -- 

CREATE DATABASE staff;

-- 1. Создать таблицу с основной информацией о сотрудниках: 
-- ФИО, дата рождения, дата начала работы, должность, уровень сотрудника (jun, middle, senior, lead), уровень зарплаты, 
-- идентификатор отдела, наличие/отсутствие прав(True/False). При этом в таблице обязательно должен быть уникальный номер для каждого сотрудника.


CREATE TYPE public.qualification_level AS ENUM
    ('jun', 'mid', 'sen', 'lead');

ALTER TYPE public.qualification_level
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS public."Employee"
(
    "Name" char(40) NOT NULL,
    "Surname" char(40) NOT NULL,
    "Midname" char(40),
    "BirthDate" date NOT NULL,
    "WorkStartDate" date NOT NULL,
    "Position" char(40),
    "QLevel" public.qualification_level NOT NULL,
    "SLevel" char(40),
    "DepartmentId" char(40) NOT NULL,
    "WithDrivingLic" boolean,
    "EmpId" char(40) NOT NULL,
    CONSTRAINT "Employee_pkey" PRIMARY KEY ("EmpId")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Employee"
    OWNER to postgres;


-- 2. Для будущих отчетов аналитики попросили вас создать еще одну таблицу с информацией по отделам – 
-- в таблице должен быть идентификатор для каждого отдела, название отдела (например. Бухгалтерский или IT отдел), ФИО руководителя и количество сотрудников.


CREATE TABLE IF NOT EXISTS public."Department"
(
	"DepId" char(40) NOT NULL,
    "Name" char(40) NOT NULL,
    "HeaderId" char(40) REFERENCES public."Employee" ("EmpId"),
    "EmpQty" smallint NOT NULL DEFAULT 0,    
    CONSTRAINT "Department_pkey" PRIMARY KEY ("DepId")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Department"
    OWNER to postgres;



-- 3. На кону конец года и необходимо выплачивать сотрудникам премию. 
-- Премия будет выплачиваться по совокупным оценкам, которые сотрудники получают в каждом квартале года. 
-- Создайте таблицу, в которой для каждого сотрудника будут его оценки за каждый квартал. Диапазон оценок от A – самая высокая, до E – самая низкая.


CREATE TYPE public.quarter_grades AS ENUM
    ('A', 'B', 'C', 'D', 'E');

ALTER TYPE public.quarter_grades
    OWNER TO postgres;


CREATE TABLE IF NOT EXISTS public."QGrade"
(
	"EmpId" char(40) REFERENCES public."Employee" ("EmpId"),
	"1QuarterGrade" public.quarter_grades ,
	"2QuarterGrade" public.quarter_grades ,
	"3QuarterGrade" public.quarter_grades ,
	"4QuarterGrade" public.quarter_grades 
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."QGrade"
    OWNER to postgres;

-- 4. Несколько уточнений по предыдущим заданиям – в первой таблице должны быть записи как минимум о 5 сотрудниках, которые работают как минимум в 2-х разных отделах. 
-- Содержимое соответствующих атрибутов остается на совесть вашей фантазии, но, желательно соблюдать осмысленность и правильно выбирать типы данных (для зарплаты – числовой тип, для ФИО – строковый и т.д.)

INSERT INTO public."Employee" (
"Name", "Surname","Midname", 
"BirthDate", "WorkStartDate", 
"Position", "QLevel", "SLevel", 
"DepartmentId", 
"WithDrivingLic", "EmpId") 
VALUES (
'John',
'Pipin',
'',
'1980-03-03'::timestamp,
'2005-10-10'::timestamp,
'DevLead',
'lead',
'300000',
'devdep1',
true,
'jp05')
returning "EmpId";

INSERT INTO public."Employee" (
"Name", "Surname","Midname", 
"BirthDate", "WorkStartDate", 
"Position", "QLevel", "SLevel", 
"DepartmentId", 
"WithDrivingLic", "EmpId") 
VALUES (
'Michael',
'Santyago',
'',
'1984-02-03'::timestamp,
'2009-10-10'::timestamp,
'Ontology developer',
'sen',
'200000',
'devdep1',
true,
'ms01')
returning "EmpId";

INSERT INTO public."Employee" (
"Name", "Surname","Midname", 
"BirthDate", "WorkStartDate", 
"Position", "QLevel", "SLevel", 
"DepartmentId", 
"WithDrivingLic", "EmpId") 
VALUES (
'Yobo',
'Makoyatsi',
'',
'1989-02-07'::timestamp,
'2011-11-11'::timestamp,
'Java developer',
'mid',
'1500000',
'devdep1',
true,
'my01')
returning "EmpId";



INSERT INTO public."Employee" (
"Name", "Surname","Midname", 
"BirthDate", "WorkStartDate", 
"Position", "QLevel", "SLevel", 
"DepartmentId", 
"WithDrivingLic", "EmpId") 
VALUES (
'Mike',
'Merondini',
'',
'1982-04-04'::timestamp,
'2005-10-10'::timestamp,
'DevLead',
'lead',
'305000',
'devdep2',
true,
'mm01')
returning "EmpId";


INSERT INTO public."Employee" (
"Name", "Surname","Midname", 
"BirthDate", "WorkStartDate", 
"Position", "QLevel", "SLevel", 
"DepartmentId", 
"WithDrivingLic", "EmpId") 
VALUES (
'Tuco',
'Damingo',
'',
'1987-03-07'::timestamp,
'2010-12-11'::timestamp,
'ML ingineer',
'sen',
'210000',
'devdep2',
true,
'td01')
returning "EmpId";

INSERT INTO public."Department" (
"DepId", "Name", "HeaderId", "EmpQty") VALUES (
'devdep1', 'Отдел разработки Бета', 'mm01', '2')
 returning "DepId";


 INSERT INTO public."Department" (
"DepId", "Name", "HeaderId", "EmpQty") VALUES (
'devdep1', 'Отдел разработки Альфа', 'jp05', '3')
 returning "DepId";


-- 5. Ваша команда расширяется и руководство запланировало открыть новый отдел – отдел Интеллектуального анализа данных. 
-- На начальном этапе в команду наняли одного руководителя отдела и двух сотрудников. 
-- Добавьте необходимую информацию в соответствующие таблицы.

INSERT INTO public."Employee" (
"Name", "Surname","Midname", 
"BirthDate", "WorkStartDate", 
"Position", "QLevel", "SLevel", 
"DepartmentId", 
"WithDrivingLic", "EmpId") 
VALUES (
'Иван',
'Мозговой',
'Федорович',
'1982-05-02'::timestamp,
'2005-11-03'::timestamp,
'Lead',
'lead',
'390000',
'iidep1',
true,
'imf01')
returning "EmpId";

INSERT INTO public."Employee" (
"Name", "Surname","Midname", 
"BirthDate", "WorkStartDate", 
"Position", "QLevel", "SLevel", 
"DepartmentId", 
"WithDrivingLic", "EmpId") 
VALUES (
'Алекс',
'Винтов',
'Федорович',
'1984-05-02'::timestamp,
'2005-11-03'::timestamp,
'ML ingineer',
'sen',
'300000',
'iidep1',
true,
'avf01')
returning "EmpId";

INSERT INTO public."Employee" (
"Name", "Surname","Midname", 
"BirthDate", "WorkStartDate", 
"Position", "QLevel", "SLevel", 
"DepartmentId", 
"WithDrivingLic", "EmpId") 
VALUES (
'Денис',
'Дорохов',
'Федорович',
'1985-05-02'::timestamp,
'2005-11-03'::timestamp,
'ingineer',
'mid',
'250000',
'iidep1',
true,
'ddf01')
returning "EmpId";


INSERT INTO public."Department" (
"DepId", "Name", "HeaderId", "EmpQty") VALUES (
'iidep1', 'Отдел Интеллектуального анализа данных', 'imf01', '3')
 returning "DepId";

-- Оценки ---

INSERT INTO public."QGrade" (
"EmpId", "1QuarterGrade", "2QuarterGrade", "3QuarterGrade", "4QuarterGrade") 
VALUES (
'ddf01','D','D','E','E')
returning "EmpId";

INSERT INTO public."QGrade" (
"EmpId", "1QuarterGrade", "2QuarterGrade", "3QuarterGrade", "4QuarterGrade") 
VALUES (
'jp05','B','C','C','B')
returning "EmpId";

INSERT INTO public."QGrade" (
"EmpId", "1QuarterGrade", "2QuarterGrade", "3QuarterGrade", "4QuarterGrade") 
VALUES (
'ms01','A','B','D','C')
returning "EmpId";

INSERT INTO public."QGrade" (
"EmpId", "1QuarterGrade", "2QuarterGrade", "3QuarterGrade", "4QuarterGrade") 
VALUES (
'my01','D','B','D','C')
returning "EmpId";




-- ·   6. Теперь пришла пора анализировать наши данные – напишите запросы для получения следующей информации:
-- o   Уникальный номер сотрудника, его ФИО и стаж работы – для всех сотрудников компании

SELECT "EmpId","Name","Surname","Midname",  AGE(CURRENT_DATE,"WorkStartDate" ) AS "Experience"  FROM public."Employee"

-- o   Уникальный номер сотрудника, его ФИО и стаж работы – только первых 3-х сотрудников

SELECT "EmpId","Name","Surname","Midname",  AGE(CURRENT_DATE,"WorkStartDate" ) AS "Experience"  FROM public."Employee" LIMIT 3

-- o   Уникальный номер сотрудников - водителей 'DevLead'

SELECT "EmpId" FROM public."Employee" WHERE "Position"='DevLead'

-- o   Выведите номера сотрудников, которые хотя бы за 1 квартал получили оценку D или E

SELECT "EmpId" FROM public."QGrade" WHERE "1QuarterGrade"='D' OR "1QuarterGrade"='E'

-- o   Выведите самую высокую зарплату в компании. 

SELECT MAX("SLevel") AS "Max salary"  FROM public."Employee"


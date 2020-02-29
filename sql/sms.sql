CREATE DATABASE IF NOT EXISTS sms;
USE sms;

DROP TABLE IF EXISTS score;
CREATE TABLE IF	NOT EXISTS score (
		id INT auto_increment UNIQUE,
		studentId INT PRIMARY KEY,
		studentName VARCHAR ( 20 ) NOT NULL,
		chinese INT,
		math INT,
		english INT,
	  sum INT 
);
DESC score;
INSERT INTO score(studentId,studentName,chinese,math,english,sum) VALUES(1200101,'千手柱间',60,90,70,chinese+math+english);
SELECT * FROM score;

DROP TABLE IF EXISTS info;
CREATE TABLE IF	NOT EXISTS info (
		id INT auto_increment UNIQUE,
		studentId INT PRIMARY KEY,
		studentName VARCHAR ( 20 ) NOT NULL,
		gender CHAR ( 1 ),
		major VARCHAR ( 20 ),
		grade INT,
    birth DATE
);
DESC info;
insert into info(studentId,studentName,gender,major,grade,birth) values(1200101,'千手柱间','男','仙法',2020,'1988-06-11');
select * from info;

DROP TABLE IF EXISTS user;
CREATE TABLE IF	NOT EXISTS user (
		id INT auto_increment UNIQUE,
		username INT PRIMARY KEY,
		password VARCHAR ( 20 ),
		category VARCHAR ( 5 ),
		signed TINYINT ( 1 ) DEFAULT 0
);
DESC user;
insert into user(username,password,category,signed) values(1994,1994,'管理员',1);
select * from user;
insert into user(username,password,category) values(1190101,null,'学生');

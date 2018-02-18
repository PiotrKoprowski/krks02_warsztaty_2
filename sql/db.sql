CREATE DATABASE krks02_warsztat2
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_general_ci;

-- Table users schema
create table users(
id bigint auto_increment,
username varchar(255) unique,
email varchar(255) unique,
password varchar(255),
person_group_id int,
primary key(id),
foreign key(person_group_id) 
references usersGroup(id));

-- Table usersGroup schema
create table usersGroup(
id int auto_increment,
name varchar(255) unique,
primary key(id));

-- Table exercise schema
create table exercise(
id int auto_increment,
title varchar(255),
description TEXT,
primary key(id));

-- Table solution schema
create table solution(
id int auto_increment,
created datetime,
updated datetime,
description TEXT,
exercise_id int,
users_id bigint,
primary key(id),
foreign key(exercise_id) 
references exercise(id),
foreign key(users_id) 
references users(id));

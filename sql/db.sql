CREATE DATABASE krks02_warsztat2
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_general_ci;

-- Table users schema
create table users(
id int auto_increment,
username varchar(255) unique,
email varchar(255) unique,
password varchar(255),
person_group_id int,
primary key(id)
);
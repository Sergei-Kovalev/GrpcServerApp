--liquibase formatted sql
--changeset Sergey Kovalev:1

CREATE TABLE users (
    id SERIAL NOT NULL,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    sex VARCHAR(10) NOT NULL,
    age INTEGER NOT NULL,
    passport_series VARCHAR(2) NOT NULL,
    passport_number VARCHAR(7) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE(passport_series, passport_number)
);
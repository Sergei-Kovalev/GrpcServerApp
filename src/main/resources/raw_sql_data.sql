DROP TABLE IF EXISTS users;

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

INSERT INTO users(name, surname, sex, age, passport_series, passport_number)
VALUES
    ('Igor', 'Ovsiannikov', 'male', 20, 'HB', '1234567'),
    ('Maria', 'Sennikova', 'female', 33, 'HB', '1234568'),
    ('Dmitriy', 'Ordzhanikidze', 'male', 18, 'HB', '6532165'),
    ('Oleg', 'Sevostianchik', 'male', 64, 'MP', '7823564'),
    ('Zhanna', 'Karpova', 'female', 28, 'MP', '5474466'),
    ('Mikhail', 'Oreshkin', 'male', 68 , 'MP', '8973552'),
    ('Olga', 'Petrova', 'female', 19, 'MP', '5444566'),
    ('Pavel', 'Kuzmenkov', 'male', 82, 'MP', '8976543'),
    ('Denis', 'Korobeinik', 'male', 38, 'MK', '5465984'),
    ('Albert', 'Einshtein', 'male', 42, 'MK', '2467899');

-- Active: 1712477837222@@127.0.0.1@5432@hrservice
CREATE TABLE IF NOT EXISTS departments(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);
CREATE TABLE IF NOT EXISTS books(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS employees(
    id           BIGSERIAL PRIMARY KEY,
    first_name   VARCHAR(255) NOT NULL UNIQUE,
    last_name    VARCHAR(255) NOT NULL UNIQUE,
    position     VARCHAR(255) NOT NULL,
    is_full_time BOOLEAN      NOT NULL
);

CREATE TABLE IF NOT EXISTS authors(
    id           BIGSERIAL PRIMARY KEY,
    first_name   VARCHAR(255) NOT NULL UNIQUE,
    last_name    VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS book_authors(
    book_id BIGSERIAL REFERENCES books (id),
    author_id   BIGSERIAL UNIQUE REFERENCES authors (id),
    PRIMARY KEY(book_id, author_id)
);
CREATE TABLE IF NOT EXISTS department_employees(
    department_id BIGSERIAL REFERENCES departments (id),
    employee_id   BIGSERIAL UNIQUE REFERENCES employees (id),
    PRIMARY KEY(department_id, employee_id)
);

CREATE TABLE IF NOT EXISTS department_managers(
    department_id BIGSERIAL UNIQUE REFERENCES departments (id),
    employee_id   BIGSERIAL UNIQUE REFERENCES employees (id),
    PRIMARY KEY(department_id, employee_id)
);
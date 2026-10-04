CREATE TABLE IF NOT EXISTS student (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name     VARCHAR(50)  NOT NULL,
    last_name      VARCHAR(50)  NOT NULL,
    student_number VARCHAR(12)  NOT NULL UNIQUE,
    email          VARCHAR(100) NOT NULL,
    major          VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS student (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name     VARCHAR(50)  NOT NULL,
    last_name      VARCHAR(50)  NOT NULL,
    student_number VARCHAR(12)  NOT NULL UNIQUE,
    email          VARCHAR(100) NOT NULL,
    major          VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS course (
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    code   VARCHAR(10)  NOT NULL UNIQUE,
    title  VARCHAR(100) NOT NULL,
    units  INT          NOT NULL
);

-- Many-to-many: which student is enrolled in which course
CREATE TABLE IF NOT EXISTS enrollment (
    student_id BIGINT NOT NULL,
    course_id  BIGINT NOT NULL,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES student (id) ON DELETE CASCADE,
    FOREIGN KEY (course_id)  REFERENCES course (id)  ON DELETE CASCADE
);

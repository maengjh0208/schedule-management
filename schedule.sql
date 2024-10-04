CREATE TABLE user
(
    user_id    INT AUTO_INCREMENT PRIMARY KEY COMMENT '작성자 고유 식별자',
    email      VARCHAR(255) NOT NULL UNIQUE '작성자 이메일',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성 날짜',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 날짜',
);

CREATE TABLE schedule
(
    schedule_id        INT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 고유 식별자',
    title              VARCHAR(255)              NOT NULL COMMENT '일정 제목',
    description        VARCHAR(1000) DEFAULT '' COMMENT '일정 내용',
    user_id            INT                       NOT NULL COMMENT '작성자 id',
    author_name        VARCHAR(255)              NOT NULL COMMENT '일정 작성 당시 작성자명',
    encrypted_password VARCHAR(255)              NOT NULL COMMENT '일정 비밀번호(암호화)',
    use_yn             CHAR(1)       DEFAULT 'Y' NOT NULL,
    created_at         DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '생성 날짜',
    updated_at         DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 날짜',
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);
CREATE TABLE IF NOT EXISTS questions (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    number VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    block_name VARCHAR(255) NOT NULL,
    media VARCHAR(255),
    question_type SMALLINT NOT NULL,
    points int NOT NULL,
    categories SMALLINT ARRAY NOT NULL,
    law TEXT,
    law_description TEXT,
    security_them_of_question TEXT,

    question_text_pl TEXT NOT NULL,
    answer_a_text_pl TEXT NOT NULL,
    answer_b_text_pl TEXT NOT NULL,
    answer_c_text_pl TEXT NOT NULL,

    question_text_eng TEXT,
    answer_a_text_eng TEXT,
    answer_b_text_eng TEXT,
    answer_c_text_eng TEXT,

    question_text_de TEXT,
    answer_a_text_de TEXT,
    answer_b_text_de TEXT,
    answer_c_text_de TEXT,

    right_answer SMALLINT NOT NULL,

    know_type SMALLINT NOT NULL DEFAULT 1,
    bookmarked BOOLEAN NOT NULL DEFAULT FALSE
)
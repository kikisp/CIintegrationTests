CREATE TABLE registration_tokens(
  	id         UUID NOT NULL,
    user_id   UUID NOT NULL,
    token  VARCHAR(255) UNIQUE,
   	status  VARCHAR(255),
   	issued_time TIMESTAMPTZ,
   	expired_time TIMESTAMPTZ,
    PRIMARY KEY (id)
);

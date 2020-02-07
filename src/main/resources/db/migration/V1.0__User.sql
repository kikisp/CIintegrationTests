CREATE TABLE users(
  	id     UUID NOT NULL,
    name  VARCHAR(255) NOT NULL,
    email  VARCHAR(50) NOT NULL UNIQUE,
    imageUrl VARCHAR(255),
    emailVerified  BOOLEAN,
    password     VARCHAR(255) NOT NULL,
    provider     VARCHAR(20) NOT NULL,
    providerId     VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
    );

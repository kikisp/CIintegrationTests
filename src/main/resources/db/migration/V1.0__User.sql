CREATE TABLE users(
  	id         UUID NOT NULL,
    name   VARCHAR(255) NOT NULL,
    password  VARCHAR(255),
    email  VARCHAR(255) NOT NULL UNIQUE,
    image_url   VARCHAR(255),
    email_verified BOOLEAN,
   	provider   VARCHAR(255) NOT NULL,
   	role   VARCHAR(255),
   	provider_id   VARCHAR(255),
   	
   	
    PRIMARY KEY (id)
);
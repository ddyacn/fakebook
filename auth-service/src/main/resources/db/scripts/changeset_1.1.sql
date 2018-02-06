--liquibase formatted SQL

--changeset ad:20170329_01 runOnChange:FALSE failOnError:FALSE CONTEXT :!test
CREATE TABLE user (
  id       BIGINT(20)   NOT NULL AUTO_INCREMENT,
  age      INT(11)      NOT NULL,
  name     VARCHAR(255) DEFAULT NULL,
  password VARCHAR(255) DEFAULT NULL,
  uuid     VARCHAR(255) DEFAULT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE authority (
  id        BIGINT(20)   NOT NULL AUTO_INCREMENT,
  authority VARCHAR(255) NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE user_authority (
  user_id       BIGINT(20)  NOT NULL,
  authority_id  BIGINT(20)  NOT NULL,

  PRIMARY KEY (user_id, authority_id),
  UNIQUE KEY uk_user_authority_authority_id (authority_id),
  CONSTRAINT fk_user_authority_user_id FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT fk_user_authority_authority_id FOREIGN KEY (authority_id) REFERENCES authority (id)
);
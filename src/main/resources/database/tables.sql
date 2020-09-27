CREATE TABLE if not exists users (
  id        INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
  firstname VARCHAR(255)    NOT NULL,
  lastname  VARCHAR(255)    NOT NULL,
  age       INT(3)          NOT NULL,
  email     VARCHAR(255)    NOT NULL,
  password  VARCHAR(255)    NOT NULL,
  flagrole  INT(1)          NOT NULL
);

CREATE TABLE if not exists roles (
  id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

CREATE TABLE if not exists user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id),

  UNIQUE (user_id, role_id)
);

INSERT INTO users VALUES (1, 'Thor','Odin', 127, 'bbb@bk.ru', '$2y$12$9Y8FVG/35cKyqFlLYnUOD.ZPkekXsIjFbjDCB3vMCYH4sL3ZKFkGK', 7);
INSERT INTO users VALUES (2, 'Person', 'Personov', 10, 'bbab@bka.ru','$2y$12$9Y8FVG/35cKyqFlLYnUOD.ZPkekXsIjFbjDCB3vMCYH4sL3ZKFkGK', 7);

INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_roles VALUES (1, 2);
INSERT INTO user_roles VALUES (1, 1);
INSERT INTO user_roles VALUES (2, 1);
USE jcw_database;
CREATE TABLE clans (
  clan_id   INT AUTO_INCREMENT,
  clan_name VARCHAR(15),
  year      YEAR,
  country   VARCHAR(25),
  CONSTRAINT PRIMARY KEY (clan_id),
  CONSTRAINT fk_users FOREIGN KEY (clan_name)
  REFERENCES users (clan_name)
);

CREATE TABLE users (
  user_id    INT(11)     AUTO_INCREMENT PRIMARY KEY,
  rating     INT,
  clan_name  VARCHAR(15),
  clan_id    INT,
  login      VARCHAR(15),
  password   VARCHAR(15),
  first_name VARCHAR(15),
  last_name  VARCHAR(15),
  email      VARCHAR(25),
  role       VARCHAR(10) DEFAULT 'user',
  status     VARCHAR(10) DEFAULT 'active',
  UNIQUE KEY clan_name_unique (clan_name)
);

ALTER TABLE users ADD role VARCHAR(10) DEFAULT 'user';

ALTER TABLE users ADD birth_date DATE;

ALTER TABLE task_orders ADD status ENUM('SOLVED', 'NOT SOLVED') DEFAULT 'NOT SOLVED';

ALTER TABLE users ADD level ENUM('BEGINNER', 'EXPERIENCED', 'EXPERT') DEFAULT 'BEGINNER';

ALTER TABLE users DROP age;

ALTER TABLE users DROP level;

ALTER TABLE users MODIFY password VARCHAR(40);

ALTER TABLE users ADD status VARCHAR(10) DEFAULT 'ACTIVE';

INSERT INTO users (rating, clan_name, clan_id, login, password, first_name, last_name, email)
VALUES (?, ?, ?, ?, ?, ?, ?, ?);


DROP TABLE clans;

DROP TABLE users;

DROP TABLE java_tasks;

SELECT *
FROM java_tasks
ORDER BY popularity DESC
LIMIT 2;

CREATE TABLE task_orders (
  order_id INT(11) PRIMARY KEY AUTO_INCREMENT,
  user_id  INT(11),
  task_id  INT(11),
  reg_date DATE,
  CONSTRAINT fk_user_id FOREIGN KEY (user_id)
  REFERENCES users (user_id),
  CONSTRAINT fk_task_id FOREIGN KEY (task_id)
  REFERENCES java_tasks (task_id)
);

INSERT INTO task_orders (user_id, task_id, reg_date, status)
VALUES (1, 2, "2017-08-13", 'NOT SOLVED');

INSERT INTO task_orders (user_id, task_id, reg_date, status)
VALUES (6, 2, "2017-08-12", 'NOT SOLVED');

INSERT INTO task_orders (user_id, task_id, reg_date)
VALUES (3, 1, "2017-07-10");

INSERT INTO task_orders (user_id, task_id, reg_date)
VALUES (2, 4, "2017-06-08");

SELECT
  u.user_id,
  u.first_name,
  u.last_name,
  jt.short_desc,
  ord.order_id,
  ord.reg_date
FROM users u INNER JOIN java_tasks jt
  INNER JOIN task_orders ord
    ON u.user_id = ord.user_id AND jt.task_id = ord.task_id
HAVING u.user_id = 2;


DROP TABLE task_orders;

SELECT * FROM task_orders
WHERE user_id = 1 AND task_orders.status = 'NOT SOLVED' LIMIT 1;

SELECT * FROM java_tasks WHERE difficulty_group = 'BEGINNER' ORDER BY task_id LIMIT 1;

SELECT * FROM java_tasks WHERE difficulty_group = 'BEGINNER' AND task_id > 8 ORDER BY task_id LIMIT 1;

ALTER TABLE task_orders ADD exec_time SMALLINT DEFAULT 0;

ALTER TABLE task_orders MODIFY exec_time BIGINT;



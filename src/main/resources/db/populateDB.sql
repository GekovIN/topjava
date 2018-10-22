DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;

ALTER SEQUENCE users_id_seq RESTART WITH 100000;
ALTER SEQUENCE meals_id_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories) VALUES

  (100000, '2017-10-15 09:30', 'Завтрак', 1000),
  (100000, '2017-10-15 12:30', 'Обед', 1000),
  (100000, '2017-10-15 20:00', 'Ужин', 500),

  (100000, '2018-10-08 10:00', 'Завтрак', 500),
  (100000, '2018-10-08 14:00', 'Обед', 1000),
  (100000, '2018-10-08 19:00', 'Ужин', 500),

  (100000, '2018-11-07 09:30', 'Завтрак', 1000),
  (100000, '2018-11-07 12:30', 'Обед', 1000),
  (100000, '2018-11-07 20:00', 'Ужин', 500),

  (100001, '2018-09-01 09:00', 'Завтрак', 500),
  (100001, '2018-09-01 13:00', 'Обед', 1000),
  (100001, '2018-09-01 18:00', 'Ужин', 700);


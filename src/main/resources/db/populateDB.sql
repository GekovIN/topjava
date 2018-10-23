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
  (100000, '2018-09-10 10:00', 'Завтрак', 500),
  (100000, '2018-09-10 13:00', 'Обед', 1000),
  (100000, '2018-09-10 19:00', 'Ужин', 1500),

  (100001, '2017-10-08 09:00', 'Завтрак', 1000),
  (100001, '2017-10-08 14:00', 'Обед', 1350),
  (100001, '2017-10-08 20:00', 'Ужин', 1750);




INSERT INTO restaurant(id, name, address)
VALUES (1, 'KFC', 'Эспланада, 2а'),
       (2, 'McDonalds', 'Большая Васильковская, 22'),
       (3, 'Сушия', 'Вадима Гетьмана, 4');

SET @YESTERDAY_DATE = DATEADD(day, -1, CAST(Current_date AS date));
INSERT INTO dish(id, name, date_added, price, restaurant_id)
VALUES (1, 'Сандерс Баскет', CURRENT_DATE, 80, 1),
       (2, 'Сандерс Пита', CURRENT_DATE, 69, 1),
       (3, 'Дискавери Баскет', CURRENT_DATE, 79, 1),
       (4, 'Донат Клубничный', CURRENT_DATE, 35, 1),
       (5, '3 Стрипса острые', @YESTERDAY_DATE, 58, 1),
       (6, '9 Стрипсов оригинальные', @YESTERDAY_DATE, 108, 1),
       (7, 'Байтс', @YESTERDAY_DATE, 63, 1),
       (8, 'Биг Мак меню', CURRENT_DATE, 80, 2),
       (9, 'Мак Чикен премьер', CURRENT_DATE, 75, 2),
       (10, 'Техас Бургер', CURRENT_DATE, 120, 2),
       (11, 'Панини Тоскана', CURRENT_DATE, 70, 2),
       (12, 'Салат Цезарь', @YESTERDAY_DATE, 50, 2),
       (13, 'Двойной Фреш МакМаффин', @YESTERDAY_DATE, 52, 2),
       (14, 'Чикен Фреш МакМаффин', @YESTERDAY_DATE, 43, 2),
       (15, 'Патти сет', CURRENT_DATE, 1695, 3),
       (16, 'Набор Комаки 1095г', CURRENT_DATE, 723, 3),
       (17, 'Набор Дзи 820г', CURRENT_DATE, 650, 3),
       (18, 'Набор Феликс Микс 360г', CURRENT_DATE, 429, 3),
       (19, 'Набор Токио Микс', @YESTERDAY_DATE, 439, 3),
       (20, 'Набор Хитовый Микс', @YESTERDAY_DATE, 659, 3),
       (21, 'Набор Лосось Микс', @YESTERDAY_DATE, 799, 3);

INSERT INTO user(id, username, email, enabled, password)
VALUES (1, 'Ryank', 'ryank@gmail.com', TRUE, '$2a$10$jyT.nlulmpax33v.yasBRu3bpBjRcbkj..bNckvXPV.l4GHLYr2.y'),
       (2, 'Bear93', 'bear@yahoo.com', TRUE, '$2a$10$qDKk5bvBNu3PXIuQazS3kuGY0DoS9UjtGthEiINKOePK6RCV9b5OO'),
       (3, 'SomeUser', 'some@gmail.com', TRUE, '$2a$10$nY5eFFJm2mFT37lrVtU5tOpO9VEl64cSHD/L.lkDNoac14coooZZy');

INSERT INTO user_roles(user_id, roles)
VALUES (1, 'ADMIN'),
       (2, 'USER'),
       (3, 'ADMIN');
INSERT INTO vote(id, vote_date, restaurant_id, user_id)
VALUES (1, CURRENT_DATE, 1, 1),
       (2, @YESTERDAY_DATE, 2, 1),
       (3, CURRENT_DATE, 1, 3);

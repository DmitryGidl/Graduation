INSERT INTO restaurant(id, name, address)
VALUES (1, 'Hachi Japanese BBQ', '56 Brewer Street'),
       (2, 'Forty Dean Street', '40 Dean Street'),
       (3, 'Ham Yard Bar and Restaurant', 'One Ham Yard');

SET @YESTERDAY_DATE = DATEADD(day, -1, CAST(Current_date AS date));
INSERT INTO dish(id, name, date_added, price, restaurant_id)
VALUES (1, 'Assorted Kimchi', CURRENT_DATE, 8, 1),
       (2, 'Edamame', CURRENT_DATE, 3.45, 1),
       (3, 'Fried Padron Peppers*', CURRENT_DATE, 3, 1),
       (4, 'Boiled Pork Belly with Spicy Sauce', CURRENT_DATE, 8.44, 1),
       (5, 'Premium Beef Tongue', @YESTERDAY_DATE, 11, 1),
       (6, 'Oyster Blade - Misuji', @YESTERDAY_DATE, 10.90, 1),
       (7, 'apanese Wagyu Beef', @YESTERDAY_DATE, 10.90, 1),
       (8, 'Sicilian Green Olives', CURRENT_DATE, 3.75, 2),
       (9, 'Pizza Inferno', CURRENT_DATE, 12, 2),
       (10, 'Penne Arrabiata', CURRENT_DATE, 11.50, 2),
       (11, 'Pistachio Tiramisu', CURRENT_DATE, 6.75, 2),
       (12, 'Quarter Pounder with Cheese', @YESTERDAY_DATE, 7, 2),
       (13, 'Cheese Platter', @YESTERDAY_DATE, 6.90, 2),
       (14, 'Rocket and Parmesan Salad', @YESTERDAY_DATE, 4.75, 2),
       (15, 'Morning Tea', CURRENT_DATE, 30, 3),
       (16, 'Ham Yard Set Menu', CURRENT_DATE, 40, 3),
       (17, 'Burrata', CURRENT_DATE, 14, 3),
       (18, 'Lobster Cocktail', CURRENT_DATE, 19.50, 3),
       (19, 'Spring Garden Salad', @YESTERDAY_DATE, 13, 3),
       (20, 'Dry Aged Beef Tartare', @YESTERDAY_DATE, 16, 3),
       (21, 'Seared Tuna', @YESTERDAY_DATE, 22, 3);

INSERT INTO user(id, username, email, enabled, password)
VALUES (1, 'Admin', 'admin@gmail.com', TRUE, '$2a$10$jyT.nlulmpax33v.yasBRu3bpBjRcbkj..bNckvXPV.l4GHLYr2.y'),
       (2, 'User', 'user@gmail.com', TRUE, '$2a$10$qDKk5bvBNu3PXIuQazS3kuGY0DoS9UjtGthEiINKOePK6RCV9b5OO'),
       (3, 'SomeUser', 'some@gmail.com', TRUE, '$2a$10$nY5eFFJm2mFT37lrVtU5tOpO9VEl64cSHD/L.lkDNoac14coooZZy');

INSERT INTO user_roles(user_id, roles)
VALUES (1, 'ADMIN'),
       (2, 'USER'),
       (3, 'ADMIN');
INSERT INTO vote(id, vote_date, restaurant_id, user_id)
VALUES (1, CURRENT_DATE, 1, 1),
       (2, @YESTERDAY_DATE, 3, 1),
       (3, CURRENT_DATE, 2, 2),
       (4, @YESTERDAY_DATE, 3, 2),
       (5, CURRENT_DATE, 1, 3),
       (6, @YESTERDAY_DATE, 2, 3);

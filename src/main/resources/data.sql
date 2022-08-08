INSERT INTO rating_mpa 
    VALUES (1, 'G'),
           (2, 'PG'),
           (3, 'PG-13'),
           (4, 'R'),
           (5, 'NC-17');

INSERT INTO films 
    VALUES (1, 'Последний богатырь: Корень зла', 'Чтобы одолеть чудище, Ваня отправляется в страну мертвых.', '2021-01-01', 121, 2),
           (2, 'МУЛЬТ в кино 121. Новый год, новый мульт!', 'В новом выпуске Тима и Тома найдут игрушку <...>', '2021-01-01', 50, 1),
           (3, 'Соседи сверху', 'Ужин двух пар превращается в сеанс семейной психотерапии.', '2021-01-07', 81, 3),
           (4, 'С днем смерти', 'Потусторонние знаки, зловещая видеозапись и ужасающие видения.', '2021-01-25', 94, 4),
           (5, 'Дрон', 'Сознание серийного убийцы перед самой смертью переносится в обычный дрон.', '2021-01-28', 82, 4);

INSERT INTO genres
    VALUES (1, 'Комедия'),
           (2, 'Драма'),
           (3, 'Мультфильм'),
           (4, 'Триллер'),
           (5, 'Документальный'),
           (6, 'Боевик'),
           (7, 'Фантастика'),
           (8, 'Фэнтези'),
           (9, 'Ужасы');

INSERT INTO films_genres
    VALUES (1, 1),
           (1, 8),
           (2, 3),
           (3, 1),
           (4, 9),
           (5, 4),
           (5, 7);

INSERT INTO users (user_id, login, name, email)
    VALUES (1, 'user_1', 'user_1', 'user_1@domen.com'),
           (2, 'user_1', 'user_2', 'user_2@domen.com'),
           (3, 'user_1', 'user_3', 'user_3@domen.com'),
           (4, 'user_1', 'user_4', 'user_4@domen.com');

INSERT INTO films_ratings
    VALUES (1, 1),
           (1, 2),
           (1, 3),
           (1, 4),
           (2, 1),
           (2, 2),
           (3, 1),
           (3, 2),
           (3, 3),
           (4, 2);

INSERT INTO friends
    VALUES (1, 2, true),
           (1, 3, false),
           (1, 4, true),
           (2, 4, true);
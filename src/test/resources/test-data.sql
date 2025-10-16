INSERT INTO mpa (name, description) VALUES ('G', 'У фильма нет возрастных ограничений');
INSERT INTO mpa (name, description) VALUES ('PG', 'Детям рекомендуется смотреть фильм с родителями');
INSERT INTO mpa (name, description) VALUES ('PG-13', 'Детям до 13 лет просмотр не желателен');
INSERT INTO mpa (name, description) VALUES ('R', 'Лицам до 17 лет просматривать фильм можно только в присутствии взрослого');
INSERT INTO mpa (name, description) VALUES ('NC-17', 'Лицам до 18 лет просмотр запрещён');

INSERT INTO genres (name, description) VALUES ('Комедия', 'Развлекательные фильмы с юмористическим сюжетом, предназначенные вызвать смех и поднять настроение.');
INSERT INTO genres (name, description) VALUES ('Драма', 'Реалистичные фильмы с глубоким эмоциональным сюжетом, focusing на человеческих отношениях и конфликтах.');
INSERT INTO genres (name, description) VALUES ('Мультфильм', 'Анимированные фильмы, созданные с помощью рисования или компьютерной графики, для всех возрастов.');
INSERT INTO genres (name, description) VALUES ('Триллер', 'Напряженные фильмы с элементами саспенса, тревоги и внезапных поворотов сюжета.');
INSERT INTO genres (name, description) VALUES ('Документальный', 'Фильмы, основанные на реальных событиях, фактах и интервью, без художественного вымысла.');
INSERT INTO genres (name, description) VALUES ('Боевик', 'Динамичные фильмы с интенсивными действиями, погонями, драками и спецэффектами.');

INSERT INTO users (email, login, name, birthday) VALUES ('email@mail.ru', 'user1', 'test1', '1990-04-15');
INSERT INTO users (email, login, name, birthday) VALUES ('email2@mail.ru', 'user2', 'test2', '1992-04-15');
INSERT INTO users (email, login, name, birthday) VALUES ('email5@mail.ru', 'user5', 'test5', '1992-04-15');

INSERT INTO films (name, description, releaseDate, duration, mpa_id) VALUES ('film1', 'descriptionFilm1', '1982-03-12', 2, 1);
INSERT INTO films (name, description, releaseDate, duration, mpa_id) VALUES ('film2', 'descriptionFilm2', '1992-03-12', 2, 2);
INSERT INTO films (name, description, releaseDate, duration, mpa_id) VALUES ('film3', 'descriptionFilm3', '1992-03-12', 2, 2);
INSERT INTO films (name, description, releaseDate, duration, mpa_id) VALUES ('film4', 'descriptionFilm4', '1992-03-12', 2, 2);

INSERT INTO likes (user_id, film_id) VALUES (1, 1);
INSERT INTO likes (user_id, film_id) VALUES (1, 2);
INSERT INTO likes (user_id, film_id) VALUES (2, 2);
INSERT INTO likes (user_id, film_id) VALUES (2, 3);

INSERT INTO genresfilms (film_id, genre_id) VALUES (2, 2);

INSERT INTO friends (user_id, friend_id) VALUES (2, 1);
INSERT INTO friends (user_id, friend_id) VALUES (3, 1);
INSERT INTO friends (user_id, friend_id) VALUES (3, 2);






CREATE DATABASE game;
\connect game
CREATE TABLE active_game (
  id SERIAL PRIMARY KEY,
  game_id int,
  white text,
  black text,
  game_status json
);

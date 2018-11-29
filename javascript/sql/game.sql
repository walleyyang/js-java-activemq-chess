CREATE DATABASE game;
\connect game
CREATE TABLE active_game (
  id SERIAL PRIMARY KEY,
  timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  game_id int,
  white text,
  black text,
  game_status json
);

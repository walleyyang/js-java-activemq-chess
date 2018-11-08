create database game;
\connect game
create table active_game (
  id int,
  white text,
  black text
);

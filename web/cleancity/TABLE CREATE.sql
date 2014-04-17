DROP TABLE reports;

CREATE TABLE reports
(
  rep_id serial NOT NULL PRIMARY KEY,
  rep_type character(25),
  rep_comment text,
  latitude character(20),
  longitude character(20),
  imgpath text
);
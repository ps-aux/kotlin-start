DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence;

DROP TABLE IF EXISTS real_estate_offer;
CREATE TABLE real_estate_offer (
  id           INTEGER,
  title        VARCHAR(256),
  url          VARCHAR(256),
  area         INTEGER,
  price        INTEGER,
  location     VARCHAR(256),
  type         VARCHAR(256),
  date         VARCHAR(256),
  scrap_source VARCHAR(256),
  scrap_date   DATE
);

CREATE TABLE post (
                      id SERIAL PRIMARY KEY,
                      name TEXT,
                      description TEXT,
                      created DATE default current_date,
                      visible BOOLEAN default false,
                      city_id integer


);

CREATE TABLE candidate (
                           id SERIAL PRIMARY KEY,
                           name TEXT,
                           description TEXT,
                           created DATE default current_date,
                           photo bytea,
                           city_id integer
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email TEXT,
    password TEXT
);

ALTER TABLE users ADD CONSTRAINT email_unique UNIQUE (email)
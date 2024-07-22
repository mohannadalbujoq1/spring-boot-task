CREATE TABLE IF NOT EXISTS director (
    director_id UUID PRIMARY KEY NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    dob DATE NOT NULL, 
    gender gender_enum NOT NULL
);
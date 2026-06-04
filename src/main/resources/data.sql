CREATE TABLE url (
    uid INT AUTO_INCREMENT NOT NULL,
    short_url VARCHAR(6) PRIMARY KEY,
    long_url TEXT NOT NULL,
    created_at datetime NOT NULL
);
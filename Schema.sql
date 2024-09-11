-- Skapa databasen KRANG om den inte redan finns
CREATE DATABASE IF NOT EXISTS KRANG;

-- Använd databasen KRANG
USE KRANG;

-- Skapa schema för användare (MySQL har inte stöd för separata scheman på samma sätt som t.ex. PostgreSQL)
-- I MySQL används scheman och databaser synonymt, så du kan hoppa över denna rad om du redan använder rätt databas.
-- Skapa tabell för användare i databasen KRANG
CREATE TABLE IF NOT EXISTS users (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Skapa ett index för att optimera sökningar baserade på användarnamn och e-post
CREATE INDEX idx_username_email ON users (username, email);

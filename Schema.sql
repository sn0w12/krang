-- Skapa databasen KRANG om den inte redan finns
CREATE DATABASE IF NOT EXISTS KRANG;

-- Använd databasen KRANG
USE KRANG;

-- Skapa schema för användare (MySQL har inte stöd för separata scheman på samma sätt som t.ex. PostgreSQL)
-- I MySQL används scheman och databaser synonymt, så du kan hoppa över denna rad om du redan använder rätt databas.


-- Skapa ett index för att optimera sökningar baserade på användarnamn och e-post
CREATE INDEX idx_username_email ON users (username, email);

### -- Crear la base de datos "EcommerceDB"
 CREATE DATABASE EcommerceDB;

### Seleccionar la base de datos recién creada
USE EcommerceDB;

### Crear la tabla "Usuarios" para almacenar información de los usuarios
CREATE TABLE usuarios (<br>
id INT AUTO_INCREMENT PRIMARY KEY,<br>
nombre VARCHAR(255) NOT NULL,<br>
correo_electronico VARCHAR(255) NOT NULL,<br>
contrasena VARCHAR(255) NOT NULL<br>
);

### CREATE USER AND GRANT PRIVILEGES
CREATE USER 'uchi'@'localhost' IDENTIFIED BY '1234';<br>
GRANT ALL PRIVILEGES ON EcommerceDB.* TO 'uchi'@'localhost';<br>
FLUSH PRIVILEGES;

# FINAL SQL

CREATE DATABASE EcommerceDB;

USE EcommerceDB;

CREATE TABLE usuarios (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(255) NOT NULL,
correo_electronico VARCHAR(255) NOT NULL,
contrasena VARCHAR(255) NOT NULL
);

CREATE USER 'uchi'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON EcommerceDB.* TO 'uchi'@'localhost';
FLUSH PRIVILEGES;

INSERT INTO usuarios (nombre, correo_electronico, contrasena)
VALUES
('Usuario1', 'usuario1@example.com', 'contrasena1'),
('Usuario2', 'usuario2@example.com', 'contrasena2'),
('Usuario3', 'usuario3@example.com', 'contrasena3');

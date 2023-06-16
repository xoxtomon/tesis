CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS "user" (
    userId UUID PRIMARY KEY NOT NULL,
    personalId INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL,
    passwordHash VARCHAR(100) NOT NULL,
    name VARCHAR(25) NOT NULL,
    lastName VARCHAR(25) NOT NULL,
    estudiante BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS estado (
    estadoId INTEGER PRIMARY KEY NOT NULL,
    descripcion VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS anteproyecto (
    anteproyectoId UUID PRIMARY KEY NOT NULL,
    noRadicacion INTEGER NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    fechaEntregaAEvaluador DATE,
    fechaEntregaDeEvaluador DATE,
    estado INTEGER NOT NULL,
    FOREIGN KEY (estado) REFERENCES estado(estadoId)
);

CREATE TABLE IF NOT EXISTS autores (
    autoresId UUID PRIMARY KEY NOT NULL,
    anteproyectoId UUID NOT NULL,
    userId UUID NOT NULL,
    FOREIGN KEY (anteproyectoId) REFERENCES anteproyecto(anteproyectoId),
    FOREIGN KEY (userId) REFERENCES "user"(userId)
);

CREATE TABLE IF NOT EXISTS "role" (
    roleId INTEGER PRIMARY KEY NOT NULL,
    descripcion VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS userroles (
    userRoleId UUID PRIMARY KEY NOT NULL,
    userId UUID NOT NULL,
    roleId INTEGER NOT NULL,
    FOREIGN KEY (userId) REFERENCES "user"(userId),
    FOREIGN KEY (roleId) REFERENCES "role"(roleId)
);

CREATE TABLE IF NOT EXISTS evaluadores (
    evaluadoresId UUID PRIMARY KEY NOT NULL,
    anteproyectoId UUID NOT NULL,
    userId UUID NOT NULL,
    FOREIGN KEY (anteproyectoId) REFERENCES anteproyecto(anteproyectoId),
    FOREIGN KEY (userId) REFERENCES "user"(userId)
);

CREATE TABLE IF NOT EXISTS proyectogrado (
    proyectoId UUID PRIMARY KEY NOT NULL,
    anteproyectoId UUID NOT NULL,
    fechaSustentacion DATE,
    notaDefinitiva NUMERIC(3,2) CHECK (notaDefinitiva BETWEEN 0.00 AND 5.00),
    nroActa INTEGER,
    mencionHonor BOOLEAN,
    gradoPostulacion VARCHAR(20) DEFAULT 'NO SE QUE VA ACA',
    entregaDocs BOOLEAN
);

-- POPULATE TABLES
-- role
INSERT INTO "role" (roleId, descripcion) VALUES
(1, 'admin'),
(2, 'estudiante'),
(3, 'director'),
(4, 'codirector'),
(5, 'evaluador'),
(6, 'jurado1'),
(7, 'jurado2');

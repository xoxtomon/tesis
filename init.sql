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
    userId UUID NOT NULL,
    roleId INTEGER NOT NULL,
    FOREIGN KEY (userId) REFERENCES "user"(userId),
    FOREIGN KEY (roleId) REFERENCES "role"(roleId)
);

CREATE TABLE IF NOT EXISTS evaluadores (
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
    gradoPostulacion DATE,
    entregaDocs BOOLEAN
);

-- POPULATE TABLES
-- role
INSERT INTO "role" (roleId, descripcion) VALUES
(1, 'ADMIN'),
(2, 'ESTUDIANTE'),
(3, 'EVALUADOR');
-- estado
INSERT INTO "estado" (estadoId, descripcion) VALUES
(1, 'APROBADO'),
(2, 'NO APROBADO'),
(3, 'PENDIENTE');
--anteproyecto
INSERT INTO anteproyecto (anteproyectoId, noRadicacion, titulo, fechaEntregaAEvaluador, fechaEntregaDeEvaluador, estado)
VALUES 
  ('2ac26b16-2b57-4fa7-b690-7dd986501a83', 1001, 'Título 1', '2023-06-01', '2023-06-05', 1),
  ('c1587360-8590-415a-8e92-66262598f0d3', 1002, 'Título 2', '2023-06-02', '2023-06-06', 2),
  ('3b25162a-274d-4c6b-bd2d-b5b1f83d3b74', 1003, 'Título 3', '2023-06-03', '2023-06-07', 3);
--proyecto
INSERT INTO proyectogrado (proyectoId, anteproyectoId, fechaSustentacion, notaDefinitiva, nroActa, mencionHonor, gradoPostulacion, entregaDocs)
VALUES 
  ('7c0e8e21-f712-4b55-b930-491f6e32c587', '2ac26b16-2b57-4fa7-b690-7dd986501a83', '2023-07-01', 4.75, 123, TRUE, '2023-01-01', TRUE),
  ('5e95191d-3f7f-4e19-ae48-7d1e15586b92', 'c1587360-8590-415a-8e92-66262598f0d3', '2023-07-02', 3.50, 456, FALSE, '2023-01-01', FALSE),
  ('c08419e9-1d74-4b70-ba89-4f748ea7b42f', '3b25162a-274d-4c6b-bd2d-b5b1f83d3b74', '2023-07-03', 4.20, 789, TRUE, '2023-01-01', TRUE);

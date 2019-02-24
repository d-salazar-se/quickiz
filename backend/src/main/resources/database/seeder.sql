-- DROP IF EXIST quickiz;

-- CREATE DATABASE quickiz;

-- USE quickiz;

--
-- Seed
--
INSERT INTO users (id, name, email, role_id) VALUES
(1, 'Isaias Cardenas', 'isaias.cardenas@usach.cl', 1),
(2, 'Diego Salazar', 'diego.salazar.se@usach.cl', 1);

INSERT INTO roles (id, name, description) VALUES
(1, 'admin', 'Administrador general de la aplicacion'),
(2, 'coordinador', 'Administrador de secciones y profesores'),
(3, 'profesor', 'Administrador de cursos'),
(4, 'alumno', 'Visualizador del sistema');

INSERT INTO questions (id, title, code, variables, pool_id, user_id) VALUES
(1, 'Pregunta uno', 'aoriesnt aoriesnt oairesnto arst', "{'key': 'value'}", 1, 2),
(2, 'Pregunta dos', 'moiearsnt  moairestna en ar', "{'foo': 'bar'}", 2, 3),
(3, 'Pregunta tres', 'moiear omoiearn stul ars', "{'key': 'value'}", 1, 1),
(4, 'Pregunta cuatro', 'ienarostmoien arsotieanr st', "{'foo': 'bar'}", 2, 1);

INSERT INTO pools (id, name) VALUES
(1, 'while'),
(2, 'for'),
(3, 'if'),
(4, 'switch');


INSERT INTO USERS (USER_ID, NAME, PASSWORD) VALUES ('mariags', 'María Gonzalez Santos', '263bce650e68ab4e23f28263760b9fa5');

INSERT INTO ROLES (ROLE_ID, DESCRIPTION) VALUES ('Rpa102Orientation', 'Personal de Orientación');

INSERT INTO PERMISSIONS (PERMISSION_ID, DESCRIPTION) VALUES ('SMS.sendToManySrudents', 'Envios de SMS a varios estudiantes simultaneamente');

INSERT INTO ROLE_PERMISSION (ROLE_ID, PERMISSION_ID) VALUES ('Rpa102Management', 'SMS.sendToManySrudents');

INSERT INTO ROLE_PERMISSION (ROLE_ID, PERMISSION_ID) VALUES ('Rpa102Orientation', 'SMS.sendToManySrudents');

INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES ('rpa', 'Rpa102Orientation');
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES ('dmoya', 'Rpa102Orientation');
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES ('evamag', 'Rpa102Orientation');
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES ('mariags', 'Rpa102Orientation');
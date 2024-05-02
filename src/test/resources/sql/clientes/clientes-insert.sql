insert into usuarios (id, username, password, role) values (500, "kiko@gmail.com", "$2a$12$BXL1Le.Dfv9BA2eFxriqmuqt3DXb00qnOb3SpC.XxJ7u6GaAZwiZm", "ROLE_CLIENTE");
insert into usuarios (id, username, password, role) values (501, "admin@gmail.com", "$2a$12$BXL1Le.Dfv9BA2eFxriqmuqt3DXb00qnOb3SpC.XxJ7u6GaAZwiZm", "ROLE_ADMIN");
insert into usuarios (id, username, password, role) values (502, "user@gmail.com", "$2a$12$BXL1Le.Dfv9BA2eFxriqmuqt3DXb00qnOb3SpC.XxJ7u6GaAZwiZm", "ROLE_CLIENTE");
insert into usuarios (id, username, password, role) values (503, "toby@email.com", "$2a$12$BXL1Le.Dfv9BA2eFxriqmuqt3DXb00qnOb3SpC.XxJ7u6GaAZwiZm", "ROLE_CLIENTE");
insert into clientes(id, nome, cpf, usuario_id) values (100, "Kiko", "12345678901", 500);
insert into clientes(id, nome, cpf, usuario_id) values (102, "Admin", "12345678902", 502);
-- Inserts para a tabela `Person`
INSERT INTO Person (id, person_type, document_number, name, birth_date, created_at)
VALUES
    (nextval('person_seq'), 1, '12345678901', 'João da Silva', '1985-05-15', CURRENT_TIMESTAMP),
    (nextval('person_seq'), 2, '98765432100', 'Maria Oliveira', '1990-10-20', CURRENT_TIMESTAMP),
    (nextval('person_seq'), 1, '45678912301', 'Carlos Souza', '1975-03-10', CURRENT_TIMESTAMP);

-- Inserts para a tabela `user_system`
INSERT INTO user_system (id, username, status, hash)
VALUES
    (1, 'talentfour', 1, '$2b$12$Q0Hry.a2fHoJ1b4UkvQDxOaZK6ifWz7v8U2yYPJwH11mFH7u2ue8C'),
    (2, 'admin', 1, '$2b$12$Q0Hry.a2fHoJ1b4UkvQDxOaZK6ifWz7v8U2yYPJwH11mFH7u2ue8C');

-- Inserts para a tabela `Address`
INSERT INTO Address (id, street, city, state)
VALUES
    (nextval('address_seq'), 'Rua das Flores', 'São Paulo', 'SP'),
    (nextval('address_seq'), 'Avenida Brasil', 'Rio de Janeiro', 'RJ'),
    (nextval('address_seq'), 'Rua 7 de Setembro', 'Curitiba', 'PR');

-- Inserts para a tabela `Cliente` (garantindo que os ids de address já existem)
INSERT INTO Cliente (id, name, email, username, address_id, created_at, change_date)
VALUES
    (nextval('cliente_seq'), 'João da Silva', 'joao.silva@example.com', 'joaosilva', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('cliente_seq'), 'Maria Oliveira', 'maria.oliveira@example.com', 'mariaoliveira', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('cliente_seq'), 'Carlos Souza', 'carlos.souza@example.com', 'carlossouza', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);





-- Inserts para a tabela `user_profile`
-- Assume que temos os IDs de perfil já criados: 1 (USER), 2 (ADVANCED), 3 (ADMINISTRATOR)
INSERT INTO user_profile (id, user_id, profile_id, created_at)
VALUES
    (nextval('user_profile_seq'), 1, 1, CURRENT_TIMESTAMP),  -- talentfour
    (nextval('user_profile_seq'), 2, 2, CURRENT_TIMESTAMP);  -- admin


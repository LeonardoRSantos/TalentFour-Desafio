INSERT INTO Cliente(id, name, email, username, address_id, created_at, change_date)
values
    (nextval('cliente_seq'), 'leonardo', 'leonardotalentfour@gmail.com', 'leonardo', 1, NOW(), NOW());
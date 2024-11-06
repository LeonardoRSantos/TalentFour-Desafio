INSERT INTO profile (id, name, system_name, created_at)
VALUES
    (nextval('profile_seq'), 'USER', 'TALENTFOUR', CURRENT_TIMESTAMP),
    (nextval('profile_seq'), 'ADVANCED', 'TALENTFOUR', CURRENT_TIMESTAMP),
    (nextval('profile_seq'), 'ADMINISTRATOR', 'TALENTFOUR', CURRENT_TIMESTAMP);
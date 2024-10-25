INSERT INTO user (id, name, created_on, updated_on, email_id, password,status, role)
VALUES (1, 'Admin', NOW(), NOW(), 'admin@example.com', '$2a$12$OdUwDIpjwtiApv4GxnKaqOOXolLlMlgQfN.bBtR6lVOs7hzc11Uwy', "ACTIVE",'ADMIN')
    ON DUPLICATE KEY UPDATE updated_on = NOW();
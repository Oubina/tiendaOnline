INSERT INTO `user`
(`id`,
`apellido`,
`email`,
`nombre`,
`password`,
`direccion`)
VALUES
('1', 'Oubiña', 'jose.oubina@outlook.es', 'Jose', '$2a$10$Pys.RONSFLZ7WVLcWoBZreLYC34V6dy43sutbaQdKA2ug9q6il/UO', 'Calle Alvedro'
);

INSERT INTO `users_roles`
(`user_id`,
`role_id`)
VALUES
(1,
2);


INSERT INTO `role`
(`id`,
`name`)
VALUES
(2, 'ROLE_USER');

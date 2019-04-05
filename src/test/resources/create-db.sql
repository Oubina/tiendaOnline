CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `apellido` varchar(255)  DEFAULT NULL,
  `email` varchar(255)  DEFAULT NULL,
  `nombre` varchar(255)  DEFAULT NULL,
  `password` varchar(255)  DEFAULT NULL,
  `direccion` varchar(255)  DEFAULT NULL,
  PRIMARY KEY (`id`),
) ;


CREATE TABLE `users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
);

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `users` (
  `user_name` VARCHAR (45) NOT NULL,
  `password` VARCHAR (100) NOT NULL,
  `enabled` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`user_name`)
)

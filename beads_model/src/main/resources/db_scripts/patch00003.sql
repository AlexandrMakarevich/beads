CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `details` longtext,
  `status` varchar(10) NOT NULL DEFAULT 'PENDING',
  `phone_number` varchar(15) DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
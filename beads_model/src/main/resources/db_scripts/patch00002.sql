CREATE TABLE `product` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `group_id` int(11) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `description` varchar(4000) DEFAULT NULL,
  `product_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_group_id` (`group_id`),
  CONSTRAINT `FK_group_id` FOREIGN KEY (`group_id`) REFERENCES `productgroup` (`ID`)
)
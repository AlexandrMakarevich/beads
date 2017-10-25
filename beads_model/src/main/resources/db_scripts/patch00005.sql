CREATE TABLE `order_order_items` (
  `order_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  KEY `fk_order_items_id` (`item_id`),
  KEY `fk_order_id1` (`order_id`),
  CONSTRAINT `fk_order_id1` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `fk_order_items_id` FOREIGN KEY (`item_id`) REFERENCES `order_item` (`id`)
)
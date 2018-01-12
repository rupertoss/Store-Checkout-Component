CREATE TABLE IF NOT EXISTS `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` longtext NOT NULL,
  `price` double NOT NULL,
  `special_price` double NOT NULL,
  `special_quantity` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cart_value` double DEFAULT NULL,
  `value` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `cart_items` (
  `id` bigint(20) NOT NULL,
  `item_quantity` int(11) DEFAULT NULL,
  `itemid` int(11) NOT NULL,
  PRIMARY KEY (`id`,`itemid`),
  CONSTRAINT `FK4k9q84oram2xig9exl0mwogtw` FOREIGN KEY (`id`) REFERENCES `cart` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `promotion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discount` double NOT NULL,
  `valid_till` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `cart` (`id`,`value`) VALUES (1,250);
INSERT INTO `cart` (`id`,`value`) VALUES (2,208.5);
INSERT INTO `cart` (`id`,`value`) VALUES (3,105);
INSERT INTO `cart` (`id`,`value`) VALUES (4,135);



INSERT INTO `cart_items` (`id`,`item_quantity`,`itemid`) VALUES (1,5,1);
INSERT INTO `cart_items` (`id`,`item_quantity`,`itemid`) VALUES (1,2,3);
INSERT INTO `cart_items` (`id`,`item_quantity`,`itemid`) VALUES (1,3,4);
INSERT INTO `cart_items` (`id`,`item_quantity`,`itemid`) VALUES (2,7,2);
INSERT INTO `cart_items` (`id`,`item_quantity`,`itemid`) VALUES (2,23,4);
INSERT INTO `cart_items` (`id`,`item_quantity`,`itemid`) VALUES (3,1,2);
INSERT INTO `cart_items` (`id`,`item_quantity`,`itemid`) VALUES (3,1,4);
INSERT INTO `cart_items` (`id`,`item_quantity`,`itemid`) VALUES (3,4,5);
INSERT INTO `cart_items` (`id`,`item_quantity`,`itemid`) VALUES (4,3,1);
INSERT INTO `cart_items` (`id`,`item_quantity`,`itemid`) VALUES (4,1,2);
INSERT INTO `cart_items` (`id`,`item_quantity`,`itemid`) VALUES (4,2,4);



INSERT INTO `item` (`id`,`description`,`price`,`special_price`,`special_quantity`) VALUES (1,'Product A',40,35,3);
INSERT INTO `item` (`id`,`description`,`price`,`special_price`,`special_quantity`) VALUES (2,'Product B',20,15,2);
INSERT INTO `item` (`id`,`description`,`price`,`special_price`,`special_quantity`) VALUES (3,'Product C',30,24,5);
INSERT INTO `item` (`id`,`description`,`price`,`special_price`,`special_quantity`) VALUES (4,'Product D',5,4.5,15);
INSERT INTO `item` (`id`,`description`,`price`,`special_price`,`special_quantity`) VALUES (5,'Product E',25,20,2);


INSERT INTO `promotion` (`id`,`code`,`description`,`discount`,`valid_till`) VALUES(1,'10%OFF','Reduces items cost by 10%','10','2019-01-30 12:00:00');
INSERT INTO `promotion` (`id`,`code`,`description`,`discount`,`valid_till`) VALUES(2,'5%OFF','Reduces items cost by 5%','5','2017-01-30 12:00:00')
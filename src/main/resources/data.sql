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
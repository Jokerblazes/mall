INSERT INTO Product(name, description, price) VALUES ('方便面', '方便面Nice', 3.0);
INSERT INTO Product(name, description, price) VALUES ('Mac', 'macbook', 8000);
INSERT INTO Product(name, description, price) VALUES ('方便面2', '老坛酸菜', 4.5);
INSERT INTO Product(name, description, price) VALUES ('方便面3', '康师傅', 3.5);

INSERT INTO Inventory (productId, count) VALUES(1, 3);
INSERT INTO Inventory (productId, count) VALUES(2, 7);
INSERT INTO Inventory (productId, count) VALUES(3, 5);
INSERT INTO Inventory (productId, count) VALUES(4, 6);

INSERT INTO LogisticsRecord(outboundTime, status) VALUES(NOW(), 'inbound');
INSERT INTO `Order`(status, createTime, payTime, logisticsId, userId) VALUES('payed', NOW(), NOW(), 1, 1);

INSERT INTO OrderItem(orderId, productId, purchaseCount, name, description, price) VALUES(1, 1, 2, '方便面', '方便面Nice', 3.0);
INSERT INTO OrderItem(orderId, productId, purchaseCount, name, description, price) VALUES(1, 2, 3, 'Mac', 'macbook', 8000);
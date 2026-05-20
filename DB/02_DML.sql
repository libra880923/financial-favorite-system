SET NAMES utf8mb4;
USE financial_db;

-- 插入測試使用者
INSERT INTO User (UserID, UserName, Email, Account) VALUES
('A123456789', '王曉明', 'test1@email.com', '1111222333'),
('B987654321', '李小華', 'test2@email.com', '2222333444');

-- 插入測試商品
INSERT INTO Product (ProductID, ProductName, Price) VALUES
('P001', '台積電股票', 600.00),
('P002', '鴻海股票', 120.50);

-- 插入測試喜好
INSERT INTO Favorite (UserID, ProductID) VALUES
('A123456789', 'P001'),
('B987654321', 'P002');

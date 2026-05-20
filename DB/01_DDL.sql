-- ============================================
-- 金融商品喜好紀錄系統 DDL
-- 資料庫：MySQL 8.0+
-- ============================================

CREATE DATABASE IF NOT EXISTS financial_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE financial_db;

-- ------------------------------------------------
-- 使用者資料表
-- ------------------------------------------------
CREATE TABLE IF NOT EXISTS `User` (
  `UserID`    VARCHAR(20)   NOT NULL COMMENT '使用者ID(PK)',
  `UserName`  VARCHAR(50)   NOT NULL COMMENT '使用者名稱',
  `Email`     VARCHAR(100)  NOT NULL COMMENT '電子郵件',
  `Account`   VARCHAR(20)   NOT NULL COMMENT '扣款帳號',
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='使用者資料表';

-- ------------------------------------------------
-- 產品資料表
-- ------------------------------------------------
CREATE TABLE IF NOT EXISTS `Product` (
  `No`          INT           NOT NULL AUTO_INCREMENT COMMENT '產品流水號(PK)',
  `ProductName` VARCHAR(100)  NOT NULL COMMENT '產品名稱',
  `Price`       DECIMAL(15,4) NOT NULL COMMENT '產品價格',
  `FeeRate`     DECIMAL(6,4)  NOT NULL COMMENT '手續費率(ex:0.1=10%)',
  PRIMARY KEY (`No`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='產品資料表';

-- ------------------------------------------------
-- 喜好清單資料表
-- ------------------------------------------------
CREATE TABLE IF NOT EXISTS `LikeList` (
  `SN`               INT            NOT NULL AUTO_INCREMENT COMMENT '流水序號(PK)',
  `UserID`           VARCHAR(20)    NOT NULL COMMENT '使用者ID(FK)',
  `ProductNo`        INT            NOT NULL COMMENT '產品流水號(FK)',
  `PurchaseQuantity` INT            NOT NULL COMMENT '購買數量',
  `Account`          VARCHAR(20)    NOT NULL COMMENT '扣款帳號',
  `TotalFee`         DECIMAL(15,4)  NOT NULL COMMENT '總手續費用(台幣)',
  `TotalAmount`      DECIMAL(15,4)  NOT NULL COMMENT '預計扣款總金額',
  `CreateTime`       DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建立時間',
  `UpdateTime`       DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',
  PRIMARY KEY (`SN`),
  CONSTRAINT `fk_likelist_user`    FOREIGN KEY (`UserID`)    REFERENCES `User`(`UserID`),
  CONSTRAINT `fk_likelist_product` FOREIGN KEY (`ProductNo`) REFERENCES `Product`(`No`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='喜好清單資料表';
USE financial_db;

DELIMITER $$

-- ===================================================
-- SP_AddLikeList：新增喜好金融商品
-- ===================================================
DROP PROCEDURE IF EXISTS `SP_AddLikeList` $$
CREATE PROCEDURE `SP_AddLikeList`(
  IN  p_UserID           VARCHAR(20),
  IN  p_ProductNo        INT,
  IN  p_PurchaseQuantity INT,
  IN  p_Account          VARCHAR(20),
  OUT p_Result           INT,       -- 0=成功, -1=失敗
  OUT p_Message          VARCHAR(200)
)
BEGIN
  DECLARE v_Price    DECIMAL(15,4);
  DECLARE v_FeeRate  DECIMAL(6,4);
  DECLARE v_TotalFee    DECIMAL(15,4);
  DECLARE v_TotalAmount DECIMAL(15,4);
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    ROLLBACK;
    SET p_Result = -1;
    SET p_Message = '新增失敗，發生系統錯誤';
  END;

  START TRANSACTION;

  -- 取得產品資訊
  SELECT Price, FeeRate INTO v_Price, v_FeeRate
  FROM Product WHERE No = p_ProductNo;

  -- 計算金額
  SET v_TotalAmount = v_Price * p_PurchaseQuantity;
  SET v_TotalFee    = v_TotalAmount * v_FeeRate;

  -- 新增喜好清單
  INSERT INTO LikeList (UserID, ProductNo, PurchaseQuantity, Account, TotalFee, TotalAmount)
  VALUES (p_UserID, p_ProductNo, p_PurchaseQuantity, p_Account, v_TotalFee, v_TotalAmount);

  COMMIT;
  SET p_Result  = 0;
  SET p_Message = '新增成功';
END $$

-- ===================================================
-- SP_QueryLikeList：查詢喜好金融商品清單
-- ===================================================
DROP PROCEDURE IF EXISTS `SP_QueryLikeList` $$
CREATE PROCEDURE `SP_QueryLikeList`(
  IN p_UserID VARCHAR(20)
)
BEGIN
  SELECT
    ll.SN,
    p.ProductName,
    p.Price,
    p.FeeRate,
    ll.PurchaseQuantity,
    ll.Account,
    ll.TotalFee,
    ll.TotalAmount,
    u.Email,
    ll.CreateTime,
    ll.UpdateTime
  FROM LikeList ll
  INNER JOIN Product p ON ll.ProductNo = p.No
  INNER JOIN `User`  u ON ll.UserID    = u.UserID
  WHERE ll.UserID = p_UserID
  ORDER BY ll.CreateTime DESC;
END $$

-- ===================================================
-- SP_UpdateLikeList：更改喜好金融商品資訊
-- ===================================================
DROP PROCEDURE IF EXISTS `SP_UpdateLikeList` $$
CREATE PROCEDURE `SP_UpdateLikeList`(
  IN  p_SN               INT,
  IN  p_ProductNo        INT,
  IN  p_PurchaseQuantity INT,
  IN  p_Account          VARCHAR(20),
  OUT p_Result           INT,
  OUT p_Message          VARCHAR(200)
)
BEGIN
  DECLARE v_Price       DECIMAL(15,4);
  DECLARE v_FeeRate     DECIMAL(6,4);
  DECLARE v_TotalFee    DECIMAL(15,4);
  DECLARE v_TotalAmount DECIMAL(15,4);
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    ROLLBACK;
    SET p_Result = -1;
    SET p_Message = '更新失敗，發生系統錯誤';
  END;

  START TRANSACTION;

  SELECT Price, FeeRate INTO v_Price, v_FeeRate
  FROM Product WHERE No = p_ProductNo;

  SET v_TotalAmount = v_Price * p_PurchaseQuantity;
  SET v_TotalFee    = v_TotalAmount * v_FeeRate;

  UPDATE LikeList
  SET ProductNo        = p_ProductNo,
      PurchaseQuantity = p_PurchaseQuantity,
      Account          = p_Account,
      TotalFee         = v_TotalFee,
      TotalAmount      = v_TotalAmount
  WHERE SN = p_SN;

  COMMIT;
  SET p_Result  = 0;
  SET p_Message = '更新成功';
END $$

-- ===================================================
-- SP_DeleteLikeList：刪除喜好金融商品
-- ===================================================
DROP PROCEDURE IF EXISTS `SP_DeleteLikeList` $$
CREATE PROCEDURE `SP_DeleteLikeList`(
  IN  p_SN      INT,
  OUT p_Result  INT,
  OUT p_Message VARCHAR(200)
)
BEGIN
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    ROLLBACK;
    SET p_Result = -1;
    SET p_Message = '刪除失敗，發生系統錯誤';
  END;

  START TRANSACTION;
  DELETE FROM LikeList WHERE SN = p_SN;
  COMMIT;
  SET p_Result  = 0;
  SET p_Message = '刪除成功';
END $$

-- ===================================================
-- SP_GetAllProducts：取得所有產品清單
-- ===================================================
DROP PROCEDURE IF EXISTS `SP_GetAllProducts` $$
CREATE PROCEDURE `SP_GetAllProducts`()
BEGIN
  SELECT No, ProductName, Price, FeeRate FROM Product ORDER BY No;
END $$

DELIMITER ;
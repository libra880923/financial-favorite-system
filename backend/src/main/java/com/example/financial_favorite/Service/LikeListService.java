package com.example.financial_favorite.Service;

import com.example.financial_favorite.DTO.ApiResponse;
import com.example.financial_favorite.DTO.LikeListRequestDTO;
import com.example.financial_favorite.DTO.LikeListResponseDTO;

import java.util.List;

public interface LikeListService {

    /**
     * 新增喜好金融商品
     *
     * @param dto 新增請求資料（使用者ID、產品編號、購買數量、扣款帳號）
     * @return 執行結果
     */
    ApiResponse<Void> addLikeList(LikeListRequestDTO dto);

    /**
     * 查詢使用者的喜好金融商品清單
     *
     * @param userID 使用者ID
     * @return 喜好清單（含產品資訊、扣款帳號、費用明細、聯絡信箱）
     */
    ApiResponse<List<LikeListResponseDTO>> queryLikeList(String userID);

    /**
     * 更改喜好金融商品資訊
     *
     * @param sn  喜好清單流水序號
     * @param dto 更新請求資料
     * @return 執行結果
     */
    ApiResponse<Void> updateLikeList(Integer sn, LikeListRequestDTO dto);

    /**
     * 刪除喜好金融商品
     *
     * @param sn 喜好清單流水序號
     * @return 執行結果
     */
    ApiResponse<Void> deleteLikeList(Integer sn);
}
package com.example.financial_favorite.Service.Impl;

import com.example.financial_favorite.DTO.*;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeListServiceImpl {

    private final EntityManager em;

    /**
     * 新增喜好金融商品（呼叫 SP_AddLikeList）
     */
    @Transactional
    public ApiResponse<Void> addLikeList(LikeListRequestDTO dto) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("SP_AddLikeList");
        query.registerStoredProcedureParameter("p_UserID", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_ProductNo", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_PurchaseQuantity", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_Account", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_Result", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_Message", String.class, ParameterMode.OUT);

        query.setParameter("p_UserID", dto.getUserID());
        query.setParameter("p_ProductNo", dto.getProductNo());
        query.setParameter("p_PurchaseQuantity", dto.getPurchaseQuantity());
        query.setParameter("p_Account", dto.getAccount());

        query.execute();

        int result = (int) query.getOutputParameterValue("p_Result");
        String message = (String) query.getOutputParameterValue("p_Message");

        return result == 0 ? ApiResponse.success(null) : ApiResponse.error(message);
    }

    /**
     * 查詢喜好金融商品清單（呼叫 SP_QueryLikeList）
     */
    @Transactional(readOnly = true)
    public ApiResponse<List<LikeListResponseDTO>> queryLikeList(String userID) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("SP_QueryLikeList");
        query.registerStoredProcedureParameter("p_UserID", String.class, ParameterMode.IN);
        query.setParameter("p_UserID", userID);
        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();
        List<LikeListResponseDTO> result = new ArrayList<>();
        for (Object[] row : rows) {
            LikeListResponseDTO dto = new LikeListResponseDTO();
            dto.setSn((Integer) row[0]);
            dto.setProductName((String) row[1]);
            dto.setPrice((BigDecimal) row[2]);
            dto.setFeeRate((BigDecimal) row[3]);
            dto.setPurchaseQuantity((Integer) row[4]);
            dto.setAccount((String) row[5]);
            dto.setTotalFee((BigDecimal) row[6]);
            dto.setTotalAmount((BigDecimal) row[7]);
            dto.setEmail((String) row[8]);
            result.add(dto);
        }
        return ApiResponse.success(result);
    }

    /**
     * 更新喜好金融商品（呼叫 SP_UpdateLikeList）
     */
    @Transactional
    public ApiResponse<Void> updateLikeList(Integer sn, LikeListRequestDTO dto) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("SP_UpdateLikeList");
        query.registerStoredProcedureParameter("p_SN", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_ProductNo", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_PurchaseQuantity", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_Account", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_Result", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_Message", String.class, ParameterMode.OUT);

        query.setParameter("p_SN", sn);
        query.setParameter("p_ProductNo", dto.getProductNo());
        query.setParameter("p_PurchaseQuantity", dto.getPurchaseQuantity());
        query.setParameter("p_Account", dto.getAccount());

        query.execute();

        int result = (int) query.getOutputParameterValue("p_Result");
        String message = (String) query.getOutputParameterValue("p_Message");

        return result == 0 ? ApiResponse.success(null) : ApiResponse.error(message);
    }

    /**
     * 刪除喜好金融商品（呼叫 SP_DeleteLikeList）
     */
    @Transactional
    public ApiResponse<Void> deleteLikeList(Integer sn) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("SP_DeleteLikeList");
        query.registerStoredProcedureParameter("p_SN", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_Result", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_Message", String.class, ParameterMode.OUT);

        query.setParameter("p_SN", sn);
        query.execute();

        int result = (int) query.getOutputParameterValue("p_Result");
        String message = (String) query.getOutputParameterValue("p_Message");

        return result == 0 ? ApiResponse.success(null) : ApiResponse.error(message);
    }
}
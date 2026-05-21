package com.example.financial_favorite.Controller;

import com.example.financial_favorite.DTO.*;
import com.example.financial_favorite.Service.LikeListService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/likelist")
@RequiredArgsConstructor
public class LikeListController {

    private final LikeListService likeListService;

    /** POST /api/v1/likelist — 新增喜好金融商品 */
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> add(@Valid @RequestBody LikeListRequestDTO dto) {
        return ResponseEntity.ok(likeListService.addLikeList(dto));
    }

    /** GET /api/v1/likelist/{userID} — 查詢喜好清單 */
    @GetMapping("/{userID}")
    public ResponseEntity<ApiResponse<List<LikeListResponseDTO>>> query(@PathVariable String userID) {
        return ResponseEntity.ok(likeListService.queryLikeList(userID));
    }

    /** PUT /api/v1/likelist/{sn} — 更改喜好金融商品 */
    @PutMapping("/{sn}")
    public ResponseEntity<ApiResponse<Void>> update(
            @PathVariable Integer sn,
            @Valid @RequestBody LikeListRequestDTO dto) {
        return ResponseEntity.ok(likeListService.updateLikeList(sn, dto));
    }

    /** DELETE /api/v1/likelist/{sn} — 刪除喜好金融商品 */
    @DeleteMapping("/{sn}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer sn) {
        return ResponseEntity.ok(likeListService.deleteLikeList(sn));
    }
}

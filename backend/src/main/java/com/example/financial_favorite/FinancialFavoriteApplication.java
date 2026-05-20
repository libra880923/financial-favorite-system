backend/src/main/java/com/example/financialfavorite/
├── FinancialFavoriteApplication.java      ← 啟動類
├── config/
│   └── SecurityConfig.java               ← Spring Security + CORS
├── controller/
│   ├── LikeListController.java           ← 喜好清單 CRUD API
│   └── ProductController.java            ← 產品查詢 API
├── service/
│   ├── LikeListService.java              ← 介面
│   └── impl/
│       └── LikeListServiceImpl.java      ← 實作
├── repository/
│   └── LikeListRepository.java           ← 呼叫 Stored Procedure
├── entity/
│   ├── User.java
│   ├── Product.java
│   └── LikeList.java
├── dto/
│   ├── LikeListRequestDTO.java           ← 新增/修改請求
│   ├── LikeListResponseDTO.java          ← 查詢回應
│   └── ApiResponse.java                  ← 統一回應格式
└── filter/
    └── XssFilter.java                    ← XSS 防護過濾器
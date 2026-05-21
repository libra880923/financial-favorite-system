# 💰 金融商品喜好紀錄系統

> 一個基於三層式架構的金融商品喜好管理平台，支援新增、查詢、修改、刪除喜好金融商品，並整合完整的安全性防護機制。

!\[Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
!\[Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen?logo=springboot)
!\[Vue.js](https://img.shields.io/badge/Vue.js-3.x-4FC08D?logo=vuedotjs)
!\[MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
!\[Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven)

\---

## 📋 目錄

* [系統架構](#系統架構)
* [功能說明](#功能說明)
* [技術棧](#技術棧)
* [專案結構](#專案結構)
* [環境需求](#環境需求)
* [安裝與啟動](#安裝與啟動)
* [資料庫設定](#資料庫設定)
* [API 文件](#api-文件)
* [安全性設計](#安全性設計)

\---

## 系統架構

```
┌─────────────────────────────────────────────┐
│              Web Layer (前端)                │
│           Vue.js + Element Plus             │
│           http://localhost:5173             │
└──────────────────┬──────────────────────────┘
                   │ RESTful API (HTTP)
┌──────────────────▼──────────────────────────┐
│           Application Layer (後端)          │
│     Spring Boot + Spring Security           │
│           http://localhost:8080             │
└──────────────────┬──────────────────────────┘
                   │ Stored Procedure
┌──────────────────▼──────────────────────────┐
│           Data Layer (資料庫)               │
│                MySQL 8.0                    │
│            financial\_db                     │
└─────────────────────────────────────────────┘
```

\---

## 功能說明

|功能|說明|
|-|-|
|➕ 新增喜好金融商品|輸入產品名稱、價格、手續費率、扣款帳號、購買數量進行新增|
|🔍 查詢喜好清單|查詢喜好商品清單，顯示預計扣款總金額、總手續費、聯絡信箱|
|✏️ 修改喜好商品|修改產品資訊、扣款帳號、購買數量，自動重新計算金額|
|🗑️ 刪除喜好商品|從清單中移除指定喜好商品|

\---

## 技術棧

### 前端

* **Vue.js 3** — 前端框架
* **Element Plus** — UI 元件庫
* **Axios** — HTTP 請求
* **Vue Router** — 路由管理
* **Pinia** — 狀態管理

### 後端

* **Spring Boot 3.2.5** — 應用程式框架
* **Spring Data JPA** — 資料存取
* **Spring Security** — 安全性防護
* **Lombok** — 減少樣板程式碼
* **Jakarta Validation** — 輸入驗證

### 資料庫

* **MySQL 8.0** — 關聯式資料庫
* **Stored Procedure** — 所有資料庫操作透過 SP 執行
* **Transaction** — 多資料表異動時確保資料一致性

### 建置工具

* **Maven 3.9** — 後端專案建置
* **npm** — 前端套件管理

\---

## 專案結構

```
financial-favorite-system/
│
├── DB/                                         # 資料庫腳本
│   ├── 01\_DDL.sql                              # 建表語法
│   ├── 02\_DML.sql                              # 測試資料
│   └── 03\_SP.sql                               # Stored Procedures
│
├── backend/                                    # Spring Boot 專案
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/example/financialfavorite/
│       │   ├── FinancialFavoriteApplication.java
│       │   ├── config/
│       │   │   └── SecurityConfig.java         # CORS + Security 設定
│       │   ├── controller/
│       │   │   ├── LikeListController.java     # 喜好清單 API
│       │   │   └── ProductController.java      # 產品查詢 API
│       │   ├── service/
│       │   │   ├── LikeListService.java        # Service 介面
│       │   │   └── impl/
│       │   │       └── LikeListServiceImpl.java # Service 實作（含 SP 呼叫）
│       │   ├── repository/
│       │   │   └── LikeListRepository.java
│       │   ├── entity/
│       │   │   ├── User.java
│       │   │   ├── Product.java
│       │   │   └── LikeList.java
│       │   ├── dto/
│       │   │   ├── LikeListRequestDTO.java
│       │   │   ├── LikeListResponseDTO.java
│       │   │   └── ApiResponse.java
│       │   └── filter/
│       │       ├── XssFilter.java              # XSS 防護過濾器
│       │       └── XssRequestWrapper.java
│       └── resources/
│           └── application.yml
│
├── frontend/                                   # Vue.js 專案
│   ├── package.json
│   └── src/
│       ├── main.js
│       ├── App.vue
│       ├── router/
│       │   └── index.js
│       ├── api/
│       │   ├── axios.js                        # Axios 基礎設定
│       │   └── likeList.js                     # API 封裝
│       └── views/
│           └── LikeListView.vue                # 主頁面
│
├── .gitignore
└── README.md
```

\---

## 環境需求

|工具|版本|下載|
|-|-|-|
|JDK|17+|https://adoptium.net|
|Maven|3.9+|https://maven.apache.org|
|Node.js|18+|https://nodejs.org|
|MySQL|8.0+|https://dev.mysql.com/downloads|
|Git|最新版|https://git-scm.com|

\---

## 安裝與啟動

### 1\. Clone 專案

```bash
git clone https://github.com/libra880923/financial-favorite-system.git
cd financial-favorite-system
```

### 2\. 資料庫初始化

登入 MySQL 後，依序執行 DB 資料夾內的 SQL 腳本：

```bash
mysql -u root -p
```

```sql
source /path/to/financial-favorite-system/DB/01\_DDL.sql
source /path/to/financial-favorite-system/DB/02\_DML.sql
source /path/to/financial-favorite-system/DB/03\_SP.sql
```

### 3\. 啟動後端

修改 `backend/src/main/resources/application.yml`，填入你的 MySQL 帳號密碼：

```yaml
spring:
  datasource:
    username: root         # 你的 MySQL 帳號
    password: yourpassword # 你的 MySQL 密碼
```

```bash
cd backend
mvn clean package -DskipTests
mvn spring-boot:run
```

> ✅ 看到 `Started FinancialFavoriteApplication` 代表後端啟動成功，Port: \*\*8080\*\*

### 4\. 啟動前端

```bash
cd frontend
npm install
npm run dev
```

> ✅ 打開瀏覽器訪問 \*\*http://localhost:5173\*\* 即可使用系統

\---

## 資料庫設定

### 資料表說明

#### User（使用者）

|欄位|型別|說明|
|-|-|-|
|UserID|VARCHAR(20)|使用者ID（Primary Key）|
|UserName|VARCHAR(50)|使用者名稱|
|Email|VARCHAR(100)|電子郵件|
|Account|VARCHAR(20)|扣款帳號|

#### Product（產品資料）

|欄位|型別|說明|
|-|-|-|
|No|INT|產品流水號（Primary Key，Auto Increment）|
|ProductName|VARCHAR(100)|產品名稱|
|Price|DECIMAL(15,4)|產品價格|
|FeeRate|DECIMAL(6,4)|手續費率（0.01 = 1%）|

#### LikeList（喜好清單）

|欄位|型別|說明|
|-|-|-|
|SN|INT|流水序號（Primary Key，Auto Increment）|
|UserID|VARCHAR(20)|使用者ID（FK → User）|
|ProductNo|INT|產品流水號（FK → Product）|
|PurchaseQuantity|INT|購買數量|
|Account|VARCHAR(20)|扣款帳號|
|TotalFee|DECIMAL(15,4)|總手續費用（台幣）|
|TotalAmount|DECIMAL(15,4)|預計扣款總金額|
|CreateTime|DATETIME|建立時間|
|UpdateTime|DATETIME|最後更新時間|

### Stored Procedures

|SP 名稱|說明|
|-|-|
|`SP\_AddLikeList`|新增喜好金融商品，自動計算 TotalFee 與 TotalAmount|
|`SP\_QueryLikeList`|查詢指定使用者的喜好清單（含 JOIN User、Product）|
|`SP\_UpdateLikeList`|更新喜好商品資訊，自動重新計算金額|
|`SP\_DeleteLikeList`|刪除指定喜好商品|
|`SP\_GetAllProducts`|取得所有產品清單（供前端下拉選單使用）|

> 所有 SP 內部皆使用 `START TRANSACTION / ROLLBACK / COMMIT` 確保資料一致性。

\---

## API 文件

Base URL：`http://localhost:8080/api/v1`

|方法|路徑|說明|Request Body|
|-|-|-|-|
|`POST`|`/likelist`|新增喜好金融商品|`LikeListRequestDTO`|
|`GET`|`/likelist/{userID}`|查詢使用者喜好清單|—|
|`PUT`|`/likelist/{sn}`|更改喜好金融商品|`LikeListRequestDTO`|
|`DELETE`|`/likelist/{sn}`|刪除喜好金融商品|—|
|`GET`|`/products`|取得所有產品清單|—|

### Request Body 範例（LikeListRequestDTO）

```json
{
  "userID": "A123456789",
  "productNo": 1,
  "purchaseQuantity": 10,
  "account": "1111222333"
}
```

### Response 格式（統一回應）

```json
{
  "code": 0,
  "message": "success",
  "data": \[ ... ]
}
```

|code|說明|
|-|-|
|`0`|執行成功|
|`-1`|執行失敗（message 欄位會說明原因）|

\---

## 安全性設計

### 防止 SQL Injection

* 全程透過 **Stored Procedure + 參數化查詢**，絕不拼接字串到 SQL。
* `EntityManager.createStoredProcedureQuery()` 搭配 `setParameter()` 傳遞參數，由 JDBC 驅動程式處理跳脫。
* DTO 層使用 `@Pattern`、`@Size`、`@NotBlank` 等 Jakarta Validation 注解，在進入 Service 前先驗證格式。

### 防止 XSS 攻擊

* **後端**：`XssFilter` 攔截所有 HTTP 請求，使用 `HtmlUtils.htmlEscape()` 對輸入參數進行 HTML 跳脫。
* **前端**：Vue 模板一律使用 `{{ }}` 插值（而非 `v-html`），Vue 框架自動對輸出內容進行 HTML 跳脫。

### CORS 設定

僅允許指定來源（`http://localhost:5173`）存取後端 API，透過 `SecurityConfig` 統一管理。

\---

## 開發分支策略

```
main          ← 正式發布版本
└── develop   ← 開發整合分支
    ├── feature/database-setup
    ├── feature/backend-setup
    └── feature/frontend-setup
```

每個功能在獨立的 `feature/\*` 分支開發，完成後透過 **Pull Request** 合併至 `develop`。

\---

## 測試帳號

資料庫初始化後，可使用以下測試帳號查詢：

|UserID|UserName|Email|Account|
|-|-|-|-|
|A123456789|王曉明|test1@email.com|1111222333|
|B987654321|李小華|test2@email.com|4444555666|




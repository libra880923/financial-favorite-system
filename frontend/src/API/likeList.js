import http from './axios'

/** 新增喜好金融商品 */
export const addLikeList = (data) => http.post('/likelist', data)

/** 查詢喜好清單 */
export const queryLikeList = (userID) => http.get(`/likelist/${userID}`)

/** 更改喜好金融商品 */
export const updateLikeList = (sn, data) => http.put(`/likelist/${sn}`, data)

/** 刪除喜好金融商品 */
export const deleteLikeList = (sn) => http.delete(`/likelist/${sn}`)

/** 取得所有產品 */
export const getAllProducts = () => http.get('/products')
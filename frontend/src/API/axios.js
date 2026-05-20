import axios from 'axios'

const instance = axios.create({
  baseURL: 'http://localhost:8080/api/v1',
  timeout: 10000,
  headers: { 'Content-Type': 'application/json' }
})

// 請求攔截器（可加 token）
instance.interceptors.request.use(config => config)

// 回應攔截器（統一錯誤處理）
instance.interceptors.response.use(
  response => response.data,
  error => {
    console.error('API Error:', error)
    return Promise.reject(error)
  }
)

export default instance
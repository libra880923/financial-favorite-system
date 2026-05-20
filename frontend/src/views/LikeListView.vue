<template>
  <div class="page-container">
    <el-card class="header-card">
      <h2>金融商品喜好紀錄系統</h2>
      <el-input
        v-model="currentUserID"
        placeholder="請輸入使用者ID（如：A123456789）"
        style="width:300px; margin-right:12px"
        clearable
      />
      <el-button type="primary" @click="loadList">查詢清單</el-button>
      <el-button type="success" @click="openAddDialog">新增商品</el-button>
    </el-card>

    <!-- 查詢結果列表 -->
    <el-card style="margin-top:20px">
      <el-table :data="likeList" stripe border v-loading="loading">
        <el-table-column prop="sn"               label="序號"       width="80"  />
        <el-table-column prop="productName"      label="產品名稱"   width="150" />
        <el-table-column prop="price"            label="產品價格"   width="120" />
        <el-table-column prop="feeRate"          label="手續費率"   width="110"
          :formatter="(row) => (row.feeRate * 100).toFixed(2) + '%'" />
        <el-table-column prop="purchaseQuantity" label="購買數量"   width="100" />
        <el-table-column prop="account"          label="扣款帳號"   width="140" />
        <el-table-column prop="totalFee"         label="總手續費"   width="120" />
        <el-table-column prop="totalAmount"      label="扣款總金額" width="130" />
        <el-table-column prop="email"            label="聯絡信箱"   min-width="180" />
        <el-table-column label="操作" width="160" fixed="right">
          <template #default="{ row }">
            <el-button size="small" type="warning" @click="openEditDialog(row)">編輯</el-button>
            <el-button size="small" type="danger"  @click="handleDelete(row.sn)">刪除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增/編輯對話框 -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? '編輯喜好商品' : '新增喜好商品'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="使用者ID" prop="userID">
          <el-input v-model="form.userID" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="選擇產品" prop="productNo">
          <el-select v-model="form.productNo" placeholder="請選擇產品" style="width:100%">
            <el-option
              v-for="p in products"
              :key="p.no"
              :label="`${p.productName}（$${p.price}）`"
              :value="p.no"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="購買數量" prop="purchaseQuantity">
          <el-input-number v-model="form.purchaseQuantity" :min="1" />
        </el-form-item>
        <el-form-item label="扣款帳號" prop="account">
          <el-input v-model="form.account" placeholder="請輸入10位數字帳號" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">確認送出</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  addLikeList, queryLikeList, updateLikeList,
  deleteLikeList, getAllProducts
} from '@/api/likeList'

const currentUserID  = ref('')
const likeList       = ref([])
const products       = ref([])
const loading        = ref(false)
const dialogVisible  = ref(false)
const isEdit         = ref(false)
const editSN         = ref(null)
const formRef        = ref(null)

const form = ref({
  userID: '', productNo: null, purchaseQuantity: 1, account: ''
})

const rules = {
  userID:           [{ required: true, message: '請輸入使用者ID', trigger: 'blur' }],
  productNo:        [{ required: true, message: '請選擇產品',    trigger: 'change' }],
  purchaseQuantity: [{ required: true, message: '請輸入購買數量', trigger: 'blur' }],
  account: [
    { required: true,  message: '請輸入扣款帳號',     trigger: 'blur' },
    { pattern: /^\d{10}$/, message: '帳號需為10位數字', trigger: 'blur' }
  ]
}

const loadList = async () => {
  if (!currentUserID.value) return ElMessage.warning('請先輸入使用者ID')
  loading.value = true
  try {
    const res = await queryLikeList(currentUserID.value)
    if (res.code === 0) likeList.value = res.data
    else ElMessage.error(res.message)
  } finally {
    loading.value = false
  }
}

const openAddDialog = () => {
  isEdit.value = false
  form.value = { userID: currentUserID.value, productNo: null, purchaseQuantity: 1, account: '' }
  dialogVisible.value = true
}

const openEditDialog = (row) => {
  isEdit.value = true
  editSN.value = row.sn
  form.value = {
    userID: currentUserID.value,
    productNo: products.value.find(p => p.productName === row.productName)?.no,
    purchaseQuantity: row.purchaseQuantity,
    account: row.account
  }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  try {
    const res = isEdit.value
      ? await updateLikeList(editSN.value, form.value)
      : await addLikeList(form.value)
    if (res.code === 0) {
      ElMessage.success(isEdit.value ? '更新成功' : '新增成功')
      dialogVisible.value = false
      await loadList()
    } else {
      ElMessage.error(res.message)
    }
  } catch (e) {
    ElMessage.error('操作失敗，請稍後再試')
  }
}

const handleDelete = async (sn) => {
  await ElMessageBox.confirm('確定要刪除此商品嗎？', '確認刪除', { type: 'warning' })
  const res = await deleteLikeList(sn)
  if (res.code === 0) {
    ElMessage.success('刪除成功')
    await loadList()
  } else {
    ElMessage.error(res.message)
  }
}

onMounted(async () => {
  const res = await getAllProducts()
  if (res.code === 0) products.value = res.data
})
</script>

<style scoped>
.page-container { padding: 24px; }
.header-card    { display: flex; align-items: center; flex-wrap: wrap; gap: 12px; }
</style>
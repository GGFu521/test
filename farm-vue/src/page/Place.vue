<template>
<el-card shadow="never" class="aui-card--fill">
  <div class="mod__place">
    <el-form :inline="true" :model="query" @keyup.enter.native="getData()">
      <el-form-item>
        <el-input v-model="query.placeName" placeholder="农场名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="query.location" placeholder="地点" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getData()" type="success">查询</el-button>
        <el-button @click="reset()">重置</el-button>
      </el-form-item>
      <el-form-item v-if="userType === 1">
        <el-button type="primary"  @click="addPlaceInfo">添加</el-button>
      </el-form-item>
      <el-form-item v-if="userType === 1">
        <el-button :disabled="multipleSelection.length === 0" type="danger"  @click="openDelBatchPlaceInfo">批量删除</el-button>
      </el-form-item>
        <el-form-item v-if="userType === 1">
            <el-button type="primary"  icon="Download" @click="openExportPlaceExcel()">导出</el-button>
        </el-form-item>

    </el-form>
    <el-table v-loading="tableDataLoading" :data="tableData" border @selection-change="selectionChange" style="width: 100%;">
    <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
               <el-table-column prop="placeName" label="农场名" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.placeName !=null">{{scope.row.placeName}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>
    <el-table-column width="120" prop="location" label="地点" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.location !=null">{{scope.row.location}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>
           <el-table-column prop="createTime" label="创建时间" width="160" header-align="center" align="center"></el-table-column>
           <el-table-column prop="updateTime" label="更新时间" width="160" header-align="center" align="center"></el-table-column>
        <el-table-column label="操作" fixed="right"  header-align="center" align="center" width="150" v-if="userType === 1">
        <template  #default="scope">
                <el-button type="success"  size="small"  @click="openUpdatePlaceInfo(scope.row)">更新</el-button>
                <el-button type="danger" size="small"  @click="openDelPlaceInfo(scope.row.id)">删除</el-button>
        </template>
    </el-table-column>
     </el-table>
      <el-pagination
              :current-page="query.pageNum"
              :page-sizes="[10, 20, 50, 100]"
              :page-size="query.pageSize"
              :total="total"
              layout="total, sizes, prev, pager, next, jumper"
              @size-change="pageSizeChange"
              @current-change="pageCurrentChange">
      </el-pagination>
      <!-- 弹窗, 新增 / 修改 -->
 <el-dialog  v-model="addOrUpdateVisible" :title="!form.id ? '新增' : '编辑'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="form" :rules="rule" ref="formRef" label-width="100px">
    
        <el-form-item label="农场名" prop="placeName">
        <el-input v-model="form.placeName" placeholder="农场名"></el-input>
    </el-form-item>
        <el-form-item label="地点" prop="location">
        <el-input v-model="form.location" placeholder="地点"></el-input>
    </el-form-item>
    
    
 </el-form>
        <template #footer>
          <el-button @click="addOrUpdateVisible = false">取消</el-button>
          <el-button type="primary" @click="formSubmitHandle()">确认</el-button>
        </template>
</el-dialog>


                                                                                                        </div>
  </el-card>
</template>

<script setup>
import { } from '@/utils/validator'
import {getPlaceList,savePlaceInfo,updatePlaceInfo,delPlaceInfo,delBatchPlaceInfo,exportPlaceExcel,uploadPlaceInfo} from '@/api/place'
import SingleUpload from '@/components/Upload.vue';
import {ref,onMounted,getCurrentInstance,computed} from 'vue';
import {useUserStore} from "@/stores";
const userStore = useUserStore();
import {useRouter,useRoute} from 'vue-router';
const router = useRouter();
const route = useRoute();
const { proxy } = getCurrentInstance()
import { ElMessageBox } from 'element-plus'

const tableData = ref([]);
const tableDataLoading = ref(false);
const query = ref({
  pageNum: 1,
  pageSize: 10,
  placeName:null,
  location:null,
});
const form = ref({});
const formRef = ref(null);
const multipleSelection = ref([]);
const total = ref(0);
const addOrUpdateVisible = ref(false);
                                                                                                                                                                                                        
const rule = ref({
  placeName: [{required: true, message: '农场名不得为空', trigger: "blur"}],
  location: [{required: true, message: '地点不得为空', trigger: "blur"}],
});

const userType = computed(() => {
  return userStore.userInfo.userType;
})

onMounted(() =>{
    getData();
});

const getData = () =>{
  let userInfo = userStore.userInfo;
  getPlaceList(query.value).then(res =>{
     tableData.value = res.data.list;
     total.value = res.data.total;
  });
}


  /**
     * 打开添加框
     */
const addPlaceInfo = () =>{
  form.value = {};
  form.value.id = null;
  if (formRef.value !==null){
      formRef.value.resetFields();
  }
  addOrUpdateVisible.value = true;
};

    /**
     * 打开更新框
     */
const openUpdatePlaceInfo = (row) =>{
  form.value = JSON.parse(JSON.stringify(row));
  if (row.content == null){
    form.value.content = ''
  }
  if (formRef.value !==null){
    formRef.value.resetFields();
  }
  addOrUpdateVisible.value = true
};

   /**
     * 按住enter键或者确定按钮提交数据,对后台发请求
     * 如果id为null,就是走添加用户信息的接口
     * 如果id不为null,就是走更新用户信息的接口
     */

const formSubmitHandle = () =>{
  formRef.value.validate(valid => {
    if(valid){
      if (form.value.id === null){
        savePlaceInfo(form.value).then(res =>{
          proxy.$message.success('添加成功');
          getData();
          addOrUpdateVisible.value = false;
         })
      }else {
        updatePlaceInfo(form.value).then(res =>{
          proxy.$message.success('更新成功');
          addOrUpdateVisible.value = false;
          getData();
       })
      }
    }
  });
};

    /**
     * 单个删除
     */
const openDelPlaceInfo = (id) =>{
  ElMessageBox.alert('确定要删除吗？', '提示', {
    type: 'warning'
  }).then(action => {
    if (action === 'confirm') {
      delPlaceInfo(id).then(res=>{
        proxy.$message.success('删除成功');
        getData();
      })
    }
  }).catch(() => {});
};

   /**
     * 批量删除
     */
const openDelBatchPlaceInfo = () =>{
  ElMessageBox.alert('确定要删除吗？', '提示', {
    type: 'warning'
  }).then(action => {
    if (action === 'confirm') {
      delBatchPlaceInfo(multipleSelection.value.join(',')).then(res=>{
        proxy.$message.success('批量删除成功');
        getData();
      })
    }
  }).catch(() => {});
};



    /**
     * 选中table表格事件
     */
const selectionChange = (val) =>{
  multipleSelection.value = [];
  val.forEach((item) => {
    multipleSelection.value.push(item.id);
  });
};

    /**
     * 重置数据
     */
const reset = () => {
  query.value.placeName = null;
  query.value.location = null;
  getData();
};
   /**
     * 分页(改变页码)
     * @param val
     */
const pageCurrentChange =(val) =>{
  query.value.pageNum = val;
  getData();
};

    /**
     * 分页(改变每页条数)
     * @param val
     */
const pageSizeChange = (val) =>{
  query.value.pageSize = val;
  getData();
};

const openExportPlaceExcel = () =>{
       exportPlaceExcel(query.value).then(res => {
           downloadFile(res);
           proxy.$message.success('导出成功')
       }).catch(error => {
           proxy.$message.error('导出失败');
       });
};
       // 文件导出
const downloadFile = (data) =>{
       if (!data) {
           return;
       }
       let url = window.URL.createObjectURL(new Blob([data]));
       let link = document.createElement('a');
       link.style.display = 'none';
       link.href = url;
       link.setAttribute('download', '农场表.xlsx');
       document.body.appendChild(link);
       link.click();
       URL.revokeObjectURL(link.href); // 释放URL 对象
       document.body.removeChild(link);
};





</script>


<style scoped>

</style>

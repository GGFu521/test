<template>
<el-card shadow="never" class="aui-card--fill">
  <div class="mod__environment">
    <el-form :inline="true" :model="query" @keyup.enter.native="getData()">
      <el-form-item>
        <el-select v-model="query.status"  placeholder="请选择状态"  style="width: 130px">
          <el-option key="1" label="正常" value="1"></el-option>
          <el-option key="2" label="异常" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="query.placeName" placeholder="农场" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="query.location" placeholder="地点" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getData()" type="success">查询</el-button>
        <el-button @click="reset()">重置</el-button>
      </el-form-item>
<!--      <el-form-item>-->
<!--        <el-button type="primary"  @click="addEnvironmentInfo">添加</el-button>-->
<!--      </el-form-item>-->

      <el-form-item v-if="userType === 1">
        <el-button :disabled="multipleSelection.length === 0" type="danger"  @click="openDelBatchEnvironmentInfo">批量删除</el-button>
      </el-form-item>
        <el-form-item>
            <el-button type="primary"  icon="Download" @click="openExportEnvironmentExcel()">导出</el-button>
        </el-form-item>

    </el-form>
    <el-table v-loading="tableDataLoading" :data="tableData" border @selection-change="selectionChange" style="width: 100%;">
    <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
    <el-table-column prop="placeName" label="农场" header-align="center" align="center">
      <template #default="scope">
          <span v-if="scope.row.placeName !=null">{{scope.row.placeName}}</span>
          <span v-else>暂无</span>
      </template>
    </el-table-column>
    <el-table-column prop="location" label="地点" header-align="center" align="center">
      <template #default="scope">
          <span v-if="scope.row.location !=null">{{scope.row.location}}</span>
          <span v-else>暂无</span>
      </template>
    </el-table-column>
                   <el-table-column prop="temValue" label="温度/°C" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.temValue !=null">{{scope.row.temValue}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>
           <el-table-column prop="humValue" label="湿度/%" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.humValue !=null">{{scope.row.humValue}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>
           <el-table-column prop="lightValue" label="光强/lux" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.lightValue !=null">{{scope.row.lightValue}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>
       <el-table-column prop="status" label="状态"  header-align="center" align="center">
     <template  #default="scope">
         <el-tag type="success" v-if="scope.row.status === 1">正常</el-tag>
         <el-tag type="success" v-if="scope.row.status === 2">异常</el-tag>
     </template>
   </el-table-column>
           <el-table-column prop="alarmInfo" label="报警信息" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.alarmInfo !=null">{{scope.row.alarmInfo}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>
           <el-table-column prop="createTime" label="创建时间" width="160" header-align="center" align="center"></el-table-column>
           <el-table-column prop="updateTime" label="更新时间" width="160" header-align="center" align="center"></el-table-column>
        <el-table-column label="操作" fixed="right"  header-align="center" align="center" width="150" v-if="userType === 1">
        <template  #default="scope">
                <el-button type="success"  size="small"  @click="openUpdateEnvironmentInfo(scope.row)">更新</el-button>
                <el-button type="danger" size="small"  @click="openDelEnvironmentInfo(scope.row.id)">删除</el-button>
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
    
    
        <el-form-item label="温度/°C" prop="temValue">
        <el-input v-model="form.temValue" placeholder="温度/°C"></el-input>
    </el-form-item>
        <el-form-item label="湿度/%" prop="humValue">
        <el-input v-model="form.humValue" placeholder="湿度/%"></el-input>
    </el-form-item>
        <el-form-item label="光强/lux" prop="lightValue">
        <el-input v-model="form.lightValue" placeholder="光强/lux"></el-input>
    </el-form-item>
          <el-form-item label="状态" prop="status">
      <el-radio-group v-model="form.status">
         <el-radio :label="1">正常</el-radio>
         <el-radio :label="2">异常</el-radio>
       </el-radio-group>
          </el-form-item>
        <el-form-item label="报警信息" prop="alarmInfo">
        <el-input v-model="form.alarmInfo" placeholder="报警信息"></el-input>
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
import {validateNumber } from '@/utils/validator'
import {getEnvironmentList,saveEnvironmentInfo,updateEnvironmentInfo,delEnvironmentInfo,delBatchEnvironmentInfo,exportEnvironmentExcel,uploadEnvironmentInfo} from '@/api/environment'
import {getPlaceList,} from '@/api/place'
import SingleUpload from '@/components/Upload.vue';
import {ref,onMounted,getCurrentInstance,computed} from 'vue';
import {useUserStore} from "@/stores";
const userStore = useUserStore();
import {useRouter,useRoute} from 'vue-router';
const router = useRouter();
const route = useRoute();
const { proxy } = getCurrentInstance()
import { ElMessageBox } from 'element-plus'
import emitter from "@/utils/eventBus";

const tableData = ref([]);
const tableDataLoading = ref(false);
const query = ref({
  pageNum: 1,
  pageSize: 10,
  placeId:null,
  status:null,
});
const form = ref({});
const formRef = ref(null);
const multipleSelection = ref([]);
const total = ref(0);
const addOrUpdateVisible = ref(false);
                                                                                                                                                                                                                                                                                                                                                                        
const rule = ref({
  temValue: [{required: true, message: '温度/°C不得为空', trigger: 'blur'},{validator:validateNumber,trigger: 'blur'}],
  humValue: [{required: true, message: '湿度/%不得为空', trigger: 'blur'},{validator:validateNumber,trigger: 'blur'}],
  lightValue: [{required: true, message: '光强/lux不得为空', trigger: 'blur'},{validator:validateNumber,trigger: 'blur'}],
  status: [{required: true, message: '状态不得为空', trigger: "change"}],
  alarmInfo: [{required: true, message: '报警信息不得为空', trigger: "blur"}],
});

emitter.on('message', (data) => {
  getData();
});

const userType = computed(() => {
  return userStore.userInfo.userType;
})

onMounted(() =>{
    getData();
});

const getData = () =>{
  let userInfo = userStore.userInfo;
  getEnvironmentList(query.value).then(res =>{
     tableData.value = res.data.list;
     total.value = res.data.total;
  });
}


  /**
     * 打开添加框
     */
const addEnvironmentInfo = () =>{
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
const openUpdateEnvironmentInfo = (row) =>{
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
        saveEnvironmentInfo(form.value).then(res =>{
          proxy.$message.success('添加成功');
          getData();
          addOrUpdateVisible.value = false;
         })
      }else {
        updateEnvironmentInfo(form.value).then(res =>{
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
const openDelEnvironmentInfo = (id) =>{
  ElMessageBox.alert('确定要删除吗？', '提示', {
    type: 'warning'
  }).then(action => {
    if (action === 'confirm') {
      delEnvironmentInfo(id).then(res=>{
        proxy.$message.success('删除成功');
        getData();
      })
    }
  }).catch(() => {});
};

   /**
     * 批量删除
     */
const openDelBatchEnvironmentInfo = () =>{
  ElMessageBox.alert('确定要删除吗？', '提示', {
    type: 'warning'
  }).then(action => {
    if (action === 'confirm') {
      delBatchEnvironmentInfo(multipleSelection.value.join(',')).then(res=>{
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
  query.value.placeId = null;
  query.value.status = null;
  query.value.placeName=null
  query.value.location=null
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

const openExportEnvironmentExcel = () =>{
       exportEnvironmentExcel(query.value).then(res => {
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
       link.setAttribute('download', '环境表.xlsx');
       document.body.appendChild(link);
       link.click();
       URL.revokeObjectURL(link.href); // 释放URL 对象
       document.body.removeChild(link);
};





</script>


<style scoped>

</style>

<template>
<el-card shadow="never" class="aui-card--fill">
  <div class="mod__setting">
    <el-form :inline="true" :model="query" @keyup.enter.native="getData()">
<!--      <el-form-item>-->
<!--        <el-button @click="getData()" type="success">查询</el-button>-->
<!--        <el-button @click="reset()">重置</el-button>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary"  @click="addSettingInfo">添加</el-button>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button :disabled="multipleSelection.length === 0" type="danger"  @click="openDelBatchSettingInfo">批量删除</el-button>-->
<!--      </el-form-item>-->

    </el-form>
    <el-table v-loading="tableDataLoading" :data="tableData" border @selection-change="selectionChange" style="width: 100%;">
    <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
               <el-table-column prop="minTemValue" label="温度最小值" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.minTemValue !=null">{{scope.row.minTemValue}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>
           <el-table-column prop="maxTemValue" label="温度最大值" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.maxTemValue !=null">{{scope.row.maxTemValue}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>
           <el-table-column prop="minHumValue" label="湿度最小值" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.minHumValue !=null">{{scope.row.minHumValue}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>
           <el-table-column prop="maxHumValue" label="湿度最大值" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.maxHumValue !=null">{{scope.row.maxHumValue}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>
           <el-table-column prop="minLightValue" label="最小光强" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.minLightValue !=null">{{scope.row.minLightValue}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>
           <el-table-column prop="maxLightValue" label="最大光强" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.maxLightValue !=null">{{scope.row.maxLightValue}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>
           <el-table-column prop="createTime" label="创建时间" width="160" header-align="center" align="center"></el-table-column>
           <el-table-column prop="updateTime" label="更新时间" width="160" header-align="center" align="center"></el-table-column>
        <el-table-column label="操作" fixed="right"  header-align="center" align="center" width="150" >
        <template  #default="scope">
                <el-button type="success"  size="small"  @click="openUpdateSettingInfo(scope.row)">更新</el-button>
<!--                <el-button type="danger" size="small"  @click="openDelSettingInfo(scope.row.id)">删除</el-button>-->
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
    
        <el-form-item label="温度最小值" prop="minTemValue">
        <el-input v-model="form.minTemValue" placeholder="温度最小值"></el-input>
    </el-form-item>
        <el-form-item label="温度最大值" prop="maxTemValue">
        <el-input v-model="form.maxTemValue" placeholder="温度最大值"></el-input>
    </el-form-item>
        <el-form-item label="湿度最小值" prop="minHumValue">
        <el-input v-model="form.minHumValue" placeholder="湿度最小值"></el-input>
    </el-form-item>
        <el-form-item label="湿度最大值" prop="maxHumValue">
        <el-input v-model="form.maxHumValue" placeholder="湿度最大值"></el-input>
    </el-form-item>
        <el-form-item label="最小光强" prop="minLightValue">
        <el-input v-model="form.minLightValue" placeholder="最小光强"></el-input>
    </el-form-item>
        <el-form-item label="最大光强" prop="maxLightValue">
        <el-input v-model="form.maxLightValue" placeholder="最大光强"></el-input>
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
import {getSettingList,saveSettingInfo,updateSettingInfo,delSettingInfo,delBatchSettingInfo} from '@/api/setting'
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
});
const form = ref({});
const formRef = ref(null);
const multipleSelection = ref([]);
const total = ref(0);
const addOrUpdateVisible = ref(false);
                                                                                                                                                                                                                                                                                                                                                                        
const rule = ref({
  minTemValue: [{required: true, message: '温度最小值不得为空', trigger: 'blur'},{validator:validateNumber,trigger: 'blur'}],
  maxTemValue: [{required: true, message: '温度最大值不得为空', trigger: 'blur'},{validator:validateNumber,trigger: 'blur'}],
  minHumValue: [{required: true, message: '湿度最小值不得为空', trigger: 'blur'},{validator:validateNumber,trigger: 'blur'}],
  maxHumValue: [{required: true, message: '湿度最大值不得为空', trigger: 'blur'},{validator:validateNumber,trigger: 'blur'}],
  minLightValue: [{required: true, message: '最小光强不得为空', trigger: 'blur'},{validator:validateNumber,trigger: 'blur'}],
  maxLightValue: [{required: true, message: '最大光强不得为空', trigger: 'blur'},{validator:validateNumber,trigger: 'blur'}],
});


const userType = computed(() => {
  return userStore.userInfo.userType;
})

onMounted(() =>{
    getData();
});

const getData = () =>{
  let userInfo = userStore.userInfo;
  getSettingList(query.value).then(res =>{
     tableData.value = res.data.list;
     total.value = res.data.total;
  });
}


  /**
     * 打开添加框
     */
const addSettingInfo = () =>{
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
const openUpdateSettingInfo = (row) =>{
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
        saveSettingInfo(form.value).then(res =>{
          proxy.$message.success('添加成功');
          getData();
          addOrUpdateVisible.value = false;
         })
      }else {
        updateSettingInfo(form.value).then(res =>{
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
const openDelSettingInfo = (id) =>{
  ElMessageBox.alert('确定要删除吗？', '提示', {
    type: 'warning'
  }).then(action => {
    if (action === 'confirm') {
      delSettingInfo(id).then(res=>{
        proxy.$message.success('删除成功');
        getData();
      })
    }
  }).catch(() => {});
};

   /**
     * 批量删除
     */
const openDelBatchSettingInfo = () =>{
  ElMessageBox.alert('确定要删除吗？', '提示', {
    type: 'warning'
  }).then(action => {
    if (action === 'confirm') {
      delBatchSettingInfo(multipleSelection.value.join(',')).then(res=>{
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






</script>


<style scoped>

</style>

<template>
<el-card shadow="never" class="aui-card--fill">
  <div class="mod__user">
    <el-form :inline="true" :model="query" @keyup.enter.native="getData()">
      <el-form-item>
        <el-input v-model="query.realName" placeholder="姓名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="query.sex"  placeholder="请选择性别"  style="width: 130px">
          <el-option key="1" label="男" value="1"></el-option>
          <el-option key="2" label="女" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="query.userType"  placeholder="请选择用户类型"  style="width: 130px">
          <el-option key="1" label="超管" value="1"></el-option>
          <el-option key="2" label="普通用户" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getData()" type="success">查询</el-button>
        <el-button @click="reset()">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"  @click="addUserInfo">添加</el-button>
      </el-form-item>
      <el-form-item>
        <el-button :disabled="multipleSelection.length === 0" type="danger"  @click="openDelBatchUserInfo">批量删除</el-button>
      </el-form-item>
<!--    <el-form-item>-->
<!--        <el-button type="primary" @click="createUserInfo">生成数据</el-button>-->
<!--    </el-form-item>-->

    </el-form>
    <el-table v-loading="tableDataLoading" :data="tableData" border @selection-change="selectionChange" style="width: 100%;">
    <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
           <el-table-column prop="userName" label="用户名" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.userName !=null">{{scope.row.userName}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>
               <el-table-column prop="realName" label="姓名" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.realName !=null">{{scope.row.realName}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>
    <el-table-column width="120" prop="phone" label="联系方式" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.phone !=null">{{scope.row.phone}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>

      <el-table-column width="160" prop="email" label="电子邮件" header-align="center" align="center">
        <template  #default="scope">
          <span v-if="scope.row.email !=null">{{scope.row.email}}</span>
          <span v-else>暂无</span>
        </template>
      </el-table-column>
    <el-table-column width="120" prop="address" label="地址" header-align="center" align="center">
         <template  #default="scope">
         <span v-if="scope.row.address !=null">{{scope.row.address}}</span>
         <span v-else>暂无</span>
         </template>
</el-table-column>
       <el-table-column prop="sex" label="性别"  header-align="center" align="center">
     <template  #default="scope">
         <el-tag type="success" v-if="scope.row.sex === 1">男</el-tag>
         <el-tag type="success" v-if="scope.row.sex === 2">女</el-tag>
     </template>
   </el-table-column>
      <el-table-column prop="imageUrl" label="图片"  header-align="center" align="center">
        <template  #default="scope">
          <el-image
              style="width: 60px; height: 60px"
              v-if="scope.row.imageUrl != null"
              :src="scope.row.imageUrl"
              :preview-src-list="[scope.row.imageUrl]">
          </el-image>
          <span v-else>暂无图片</span>
        </template>
      </el-table-column>
       <el-table-column prop="userType" label="用户类型"  header-align="center" align="center">
     <template  #default="scope">
         <el-tag type="success" v-if="scope.row.userType === 1">超管</el-tag>
         <el-tag type="success" v-if="scope.row.userType === 2">普通用户</el-tag>
     </template>
   </el-table-column>
           <el-table-column prop="createTime" label="创建时间" width="160" header-align="center" align="center"></el-table-column>
           <el-table-column prop="updateTime" label="更新时间" width="160" header-align="center" align="center"></el-table-column>
    <el-table-column label="操作" fixed="right" header-align="center" align="center" width="250">
        <template  #default="scope">
            <el-button type="primary"  size="small" @click="openPasswordInfo(scope.row)">修改密码</el-button>
            <el-button type="success" size="small" @click="openUpdateUserInfo(scope.row)">更新</el-button>
            <el-button type="danger" size="small" @click="openDelUserInfo(scope.row.id)">删除</el-button>
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

        <el-form-item label="用户名" prop="userName">
        <el-input v-model="form.userName" placeholder="用户名"></el-input>
    </el-form-item>
        <el-form-item label="密码" prop="password" v-if="form.id == null">
        <el-input v-model="form.password" type="password" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="confirmPassword" v-if="form.id == null">
        <el-input v-model="form.confirmPassword" type="password" placeholder="确认密码"></el-input>
    </el-form-item>
        <el-form-item label="姓名" prop="realName">
        <el-input v-model="form.realName" placeholder="姓名"></el-input>
    </el-form-item>
        <el-form-item label="联系方式" prop="phone">
        <el-input v-model="form.phone" placeholder="联系方式"></el-input>
    </el-form-item>
      <el-form-item label="电子邮件" prop="email">
        <el-input v-model="form.email" placeholder="电子邮件"></el-input>
      </el-form-item>
        <el-form-item label="地址" prop="address">
        <el-input v-model="form.address" placeholder="地址"></el-input>
    </el-form-item>
          <el-form-item label="性别" prop="sex">
      <el-radio-group v-model="form.sex">
         <el-radio :label="1">男</el-radio>
         <el-radio :label="2">女</el-radio>
       </el-radio-group>
          </el-form-item>
          <el-form-item label="用户类型" prop="userType">
      <el-radio-group v-model="form.userType">
         <el-radio :label="1">超管</el-radio>
         <el-radio :label="2">普通用户</el-radio>
       </el-radio-group>
          </el-form-item>
      <el-form-item label="图片" prop="imageUrl">
        <single-upload v-model="form.imageUrl"></single-upload>
      </el-form-item>

         </el-form>
        <template #footer>
          <el-button @click="addOrUpdateVisible = false">取消</el-button>
          <el-button type="primary" @click="formSubmitHandle()">确认</el-button>
        </template>
</el-dialog>


    <el-dialog v-model="addOrUpdateVisible2" title="修改密码" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form :model="form" :rules="passwordRule" ref="passwordFormRef"  label-width="100px">
            <el-form-item label="新密码" prop="password">
                <el-input v-model="form.password"
                          placeholder="新密码"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
                <el-input v-model="form.confirmPassword" placeholder="确认密码"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <el-button @click="addOrUpdateVisible2 = false">取消</el-button>
            <el-button type="primary" @click="updatePassword()">确认</el-button>
        </template>
    </el-dialog>
                                                                                                                                                                                                                                                    </div>
  </el-card>
</template>

<script setup>
import {validateEmail,validatePassword,validatePhone, } from '@/utils/validator'
import {getUserList,saveUserInfo,updateUserInfo,delUserInfo,delBatchUserInfo,createUserData} from '@/api/user'
import SingleUpload from '@/components/Upload.vue';
import {ref,onMounted,getCurrentInstance,computed} from 'vue';
import {useUserStore} from "@/stores";
const userStore = useUserStore();
const { proxy } = getCurrentInstance()
import { ElMessageBox } from 'element-plus'

const validateNewPassword = (rule, value, callback) => {
  if (value === '') {
      callback(new Error('请输入密码'));
  } else {
      if (form.value.confirmPassword !== '') {
          passwordFormRef.value.validateField('confirmPassword');
      }
      callback();
  }
};
const validateConfirmPassword = (rule, value, callback) => {
  if (value === '') {
      callback(new Error('请再次输入密码'));
  } else if (value !== form.value.password) {
      callback(new Error('两次输入密码不一致!'));
  } else {
      callback();
  }
};

const validateNewPasswordOne = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入密码'));
  } else {
    if (form.value.confirmPassword !== '') {
      formRef.value.validateField('confirmPassword');
    }
    callback();
  }
};
const validateConfirmPasswordOne = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'));
  } else if (value !== form.value.password) {
    callback(new Error('两次输入密码不一致!'));
  } else {
    callback();
  }
};
const tableData = ref([]);
const tableDataLoading = ref(false);
const query = ref({
  pageNum: 1,
  pageSize: 10,
  realName:null,
  sex:null,
  userType:null,
});
const form = ref({});
const formRef = ref(null);
const multipleSelection = ref([]);
const total = ref(0);
const addOrUpdateVisible = ref(false);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
const rule = ref({
  email: [{required: false, message: '电子邮件不得为空', trigger: 'blur'},{validator:validateEmail,trigger: 'blur'}],
  userName: [{required: true, message: '用户名不得为空', trigger: "blur"}],
  password:[
    { required: true, message: '密码不得为空', trigger: 'blur' },
    { min: 5, max: 16, message: '长度在 5 到 16 个字符', trigger: 'blur' },
    { validator: validateNewPasswordOne, trigger: 'blur' }
   ],
   confirmPassword:[
    { required: true, message: '请确认密码', trigger: 'blur' },
    { min: 5, max: 16, message: '长度在 5 到 16 个字符', trigger: 'blur' },
    { validator: validateConfirmPasswordOne, trigger: 'blur', required: true }
   ],
  realName: [{required: true, message: '姓名不得为空', trigger: "blur"}],
  phone: [{required: false, message: '联系方式不得为空', trigger: 'blur'},{validator:validatePhone,trigger: 'blur'}],
  address: [{required: true, message: '地址不得为空', trigger: "blur"}],
  sex: [{required: true, message: '性别不得为空', trigger: "change"}],
  userType: [{required: true, message: '用户类型不得为空', trigger: "change"}],
});

const passwordRule = ref({
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
        { validator: validateNewPassword, trigger: 'blur' }
    ],
    confirmPassword:[
        { required: true, message: '请确认密码', trigger: 'blur' },
        { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
        { validator: validateConfirmPassword, trigger: 'blur', required: true }
    ],
});
    const addOrUpdateVisible2 = ref(false);
    const passwordFormRef = ref(null);

const userType = computed(() => {
  return userStore.userInfo.userType;
})

onMounted(() =>{
    getData();
});

const getData = () =>{
  let userInfo = userStore.userInfo;
  getUserList(query.value).then(res =>{
     tableData.value = res.data.list;
     total.value = res.data.total;
  });
}


  /**
     * 打开添加框
     */
const addUserInfo = () =>{
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
const openUpdateUserInfo = (row) =>{
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
        saveUserInfo(form.value).then(res =>{
          proxy.$message.success('添加成功');
          getData();
          addOrUpdateVisible.value = false;
         })
      }else {
        updateUserInfo(form.value).then(res =>{
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
const openDelUserInfo = (id) =>{
  ElMessageBox.alert('确定要删除吗？', '提示', {
    type: 'warning'
  }).then(action => {
    if (action === 'confirm') {
      delUserInfo(id).then(res=>{
        proxy.$message.success('删除成功');
        getData();
      })
    }
  }).catch(() => {});
};

   /**
     * 批量删除
     */
const openDelBatchUserInfo = () =>{
  ElMessageBox.alert('确定要删除吗？', '提示', {
    type: 'warning'
  }).then(action => {
    if (action === 'confirm') {
      delBatchUserInfo(multipleSelection.value.join(',')).then(res=>{
        proxy.$message.success('批量删除成功');
        getData();
      })
    }
  }).catch(() => {});
};

const  openPasswordInfo =(row) =>{
     form.value = {};
     form.value.id = row.id
     addOrUpdateVisible2.value = true;
};
const updatePassword = () =>{
passwordFormRef.value.validate(valid =>{
    if(valid){
    updateUserInfo(form.value).then(res =>{
      proxy.$message.success('更新成功');
      getData();
      addOrUpdateVisible2.value = false;
      })
    }
    })
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
  query.value.realName = null;
  query.value.sex = null;
  query.value.userType = null;
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

const createUserInfo = () =>{
        createUserData().then(res =>{
            getData();
            proxy.$message.success('操作成功')
        })
    }


</script>


<style scoped>

</style>

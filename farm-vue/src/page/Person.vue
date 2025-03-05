<template>
    <el-card >
        <div class="person">
        <div class="left">
          <el-image :src="userInfo.imageUrl" style="width: 300px;height: 300px"></el-image>
        </div>
        <div class="right">
            <el-form :model="userInfo" :rules="rule" ref="formRef" @keyup.enter.native="formSubmitHandle()" label-width="100px">
        <el-form-item label="学号" prop="userName">
            <el-input v-model="userInfo.userName" placeholder="学号"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
            <el-input v-model="userInfo.password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="realName">
            <el-input v-model="userInfo.realName" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
            <el-input v-model="userInfo.phone" placeholder="联系方式"></el-input>
        </el-form-item>
        <el-form-item label="邮箱号" prop="email">
            <el-input v-model="userInfo.email" placeholder="邮箱号"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
            <el-input v-model="userInfo.address" placeholder="地址"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
            <el-radio-group v-model="userInfo.sex">
                    <el-radio :label="1">男</el-radio>
                    <el-radio :label="2">女</el-radio>
            </el-radio-group>
        </el-form-item>
              <el-form-item label="头像" prop="address">
                <single-upload v-model="userInfo.imageUrl"></single-upload>
              </el-form-item>
        <el-form-item>


            <el-button type="primary" @click="formSubmitHandle()">确定修改</el-button>
            <el-button type="success" style="margin-left: 150px" @click="openPassword">修改密码</el-button>
        </el-form-item>

    </el-form>
      </div>
</div>
        <el-dialog v-model="addOrUpdateVisible" title="修改密码" :close-on-click-modal="false" :close-on-press-escape="false">
            <el-form :model="form" :rules="passwordRule" ref="passwordFormRef"  label-width="100px">
                <el-form-item label="新密码" prop="password">
                    <el-input type="password" v-model="form.password"
                              placeholder="新密码"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="confirmPassword">
                    <el-input type="password" v-model="form.confirmPassword" placeholder="确认密码"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="addOrUpdateVisible = false">取消</el-button>
                <el-button type="primary" @click="openUpdatePassword()">确认</el-button>
            </template>
        </el-dialog>

    </el-card>
</template>

<script setup>
import {ref,onMounted,getCurrentInstance} from 'vue';
import {useUserStore} from "@/stores";
const userStore = useUserStore();
const { proxy } = getCurrentInstance()
import SingleUpload from '@/components/Upload.vue';
import { getUserInfo, updatePassword, updateUserInfo} from '@/api/user';
import {validatePhone,validateEmail, } from '@/utils/validator';
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

const form = ref({});
const addOrUpdateVisible = ref(false);
const userInfo = ref({});
const imageUrl = ref('');
const formRef = ref(null);
const rechargeFormRef = ref(null);
const passwordFormRef = ref(null);
const rule = ref({
                imageUrl: [{required: false, message: '图片不得为空', trigger: "blur"}],
            userName: [{required: false, message: '学号不得为空', trigger: "blur"}],
            password: [{required: true, message: '密码不得为空', trigger: "blur"}],
            realName: [{required: false, message: '姓名不得为空', trigger: "blur"}],
            phone: [{required: false, message: '联系方式不得为空', trigger: 'blur'},{validator:validatePhone,trigger: 'blur'}],

            sex: [{required: true, message: '性别不得为空', trigger: "change"}],
            userType: [{required: true, message: '用户类型不得为空', trigger: "change"}],
        });
const passwordRule = ref({
        password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 5, max: 16, message: '长度在 5 到 16 个字符', trigger: 'blur' },
            { validator: validateNewPassword, trigger: 'blur' }
        ],
        confirmPassword:[
            { required: true, message: '请确认密码', trigger: 'blur' },
            { min: 5, max: 16, message: '长度在 5 到 16 个字符', trigger: 'blur' },
            { validator: validateConfirmPassword, trigger: 'blur', required: true }
        ],
});

onMounted(() =>{
   getData();
});

const getData = () =>{
     getUserInfo({id:userStore.userInfo.id}).then(res =>{
       userInfo.value = res.data;
     });
};

const formSubmitHandle = () =>{
    formRef.value.validate(valid => {
        if(valid){
            updateUserInfo(userInfo.value).then(res =>{
                proxy.$message.success('修改成功');
                getData();
            })
        }
    },
)};

const openPassword = () =>{
    form.value = {};
    addOrUpdateVisible.value = true;
};

const openUpdatePassword = () =>{
    passwordFormRef.value.validate(valid => {
       if(valid){
        form.value.id = userStore.userInfo.id;
        updatePassword(form.value).then(res =>{
            addOrUpdateVisible.value = false;
            proxy.$message.success('密码更新成功');
       })
    }
   })
};

</script>

<style scoped>
    .person{
        display: inline-flex;
        width: 1000px;
        /*height: 400px;*/
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
    }
    .left{
        flex: 1;
        /*height: 400px;*/
        padding: 5px;
        /*background-color: red;*/
        position: relative;
    }
    .right{
        flex: 3;
        /*height: 400px;*/
        padding: 20px;
        /*background-color: #00a854;*/
    }

</style>

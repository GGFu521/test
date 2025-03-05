<template>
  <div>
    <header class="login-header">
      <div class="container2 m-top-20">
     
      </div>
    </header>
    <section class="login-section">
      <div class="login-wrapper">
        <nav>
          <a href="javascript:;">账户注册</a>
        </nav>
        <div class="account-box">
          <div class="form">
            <el-form ref="registerRef" :model="form" :rules="rules" label-position="right" label-width="100px" status-icon>
              <el-form-item prop="userName" label="账户">
                <el-input v-model="form.userName" placeholder="请输入账户" />
              </el-form-item>
              <el-form-item prop="password" label="密码">
                <el-input v-model="form.password" placeholder="请输入密码"/>
              </el-form-item>
              <el-form-item prop="realName" label="姓名">
                <el-input v-model="form.realName" placeholder="请输入姓名"/>
              </el-form-item>
              <el-form-item prop="phone" label="手机号">
                <el-input v-model="form.phone" placeholder="请输入手机号"/>
              </el-form-item>
              <el-form-item prop="email" label="邮箱">
                <el-input v-model="form.email" placeholder="请输入邮箱" />
              </el-form-item>
              <el-form-item label="性别" prop="sex">
                <el-radio-group v-model="form.sex">
                  <el-radio :label="1">男</el-radio>
                  <el-radio :label="2">女</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item prop="address" label="地址">
                <el-input v-model="form.address" placeholder="请输入地址"></el-input>
              </el-form-item>
              <el-form-item  prop="imageUrl" label="头像">
                <single-upload v-model="form.imageUrl"></single-upload>
              </el-form-item>
              <el-button size="large" class="subBtn" @click="toRegister">点击注册</el-button>
            </el-form>
          </div>
        </div>
      </div>
    </section>


  </div>
</template>

<script setup>
import {ref,getCurrentInstance} from 'vue';
const { proxy } = getCurrentInstance()
import {saveUserInfo } from '@/api/user';
import {useRouter} from 'vue-router';
import SingleUpload from "@/components/Upload.vue";
import {validateEmail, validatePhone} from "@/utils/validator";
const router = useRouter();
const form = ref({});
const loginTitle = ref('首页')
const rules = ref({
  user_name: [{required: true, message: '学号不得为空', trigger: "blur"}],
  password: [{required: true, message: '密码不得为空', trigger: "blur"}],
  real_name: [{required: true, message: '姓名不得为空', trigger: "blur"}],
  sex: [{required: true, message: '性别不得为空', trigger: "change"}],
  phone: [{required: true, message: '联系方式不得为空', trigger: 'blur'},{validator:validatePhone,trigger: 'blur'}],
  email: [{required: true, message: '邮箱不得为空', trigger: 'blur'},{validator:validateEmail,trigger: 'blur'}],
  user_type: [{required: true, message: '用户类型不得为空', trigger: "change"}],
  image_url: [{required: true, message: '图片不得为空', trigger: "blur"}],
});
const registerRef = ref(null);
const toRegister = () =>{
  registerRef.value.validate(valid => {
    if(valid){
      form.value.userType = 2;
      saveUserInfo(form.value).then(res =>{
        if (res.code === 1){
          proxy.$message.success('注册成功,现在可以去登录了');
          setTimeout(() =>{
            router.push('/login')
          },2000)
        }else {
          proxy.$message.error('注册失败');
        }
      })
    }
  });
};
</script>

<style scoped>
.login-header {
  background: #fff;
  border-bottom: 1px solid #e4e4e4;
  height: 120px;
  .container2 {
    display: flex;
    align-items: flex-end;
    justify-content: space-between;
    width: 1240px;
    margin: 0 auto;
    position: relative;
  }

  .logo {
    width: 200px;
    line-height: 120px;
    a {
      /*display: block;*/
      height: 132px;
      width: 100%;
      text-indent: -9999px;
      /*color: black;*/
      color: #00a854;

    }
  }

  a{
    text-decoration: none;
    outline: none;
    color: #00a854;
  }

  .sub {
    flex: 1;
    font-size: 24px;
    font-weight: normal;
    margin-bottom: 38px;
    margin-left: 20px;
    color: #666;
  }

  .entry {
    width: 120px;
    margin-bottom: 38px;
    font-size: 16px;

    i {
      font-size: 14px;
      color: #27ba9b;
      letter-spacing: -5px;
    }
  }
}

.login-section {
  background: url('@/assets/image/login-bg.png') no-repeat center / cover;
  height: 588px;
  position: relative;

  .login-wrapper {
    width: 480px;
    background: #fff;
    position: absolute;
    left: 40%;
    top: -24px;
    transform: translate3d(100px, 0, 0);
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);

    nav {
      font-size: 14px;
      height: 55px;
      margin-bottom: 20px;
      border-bottom: 1px solid #f5f5f5;
      display: flex;
      padding: 0 40px;
      text-align: right;
      align-items: center;

      a {
        flex: 1;
        line-height: 1;
        display: inline-block;
        font-size: 18px;
        position: relative;
        text-align: center;
        color: black;
        text-decoration: none;
        outline: none;
      }
    }
  }
}

.login-footer {
  padding: 30px 0 50px;
  background: #fff;

  p {
    text-align: center;
    color: #999;
    padding-top: 20px;

    a {
      line-height: 1;
      padding: 0 10px;
      color: #999;
      display: inline-block;

      ~a {
        border-left: 1px solid #ccc;
      }
    }
  }
}

.account-box {
  .toggle {
    padding: 15px 40px;
    text-align: right;

    a {
      color: #27ba9b;

      i {
        font-size: 14px;
      }
    }
  }

  .form {
    padding: 0 20px 20px 20px;

    &-item {
      margin-bottom: 28px;

      .input {
        position: relative;
        height: 36px;

        >i {
          width: 34px;
          height: 34px;
          background: #cfcdcd;
          color: #fff;
          position: absolute;
          left: 1px;
          top: 1px;
          text-align: center;
          line-height: 34px;
          font-size: 18px;
        }

        input {
          padding-left: 44px;
          border: 1px solid #cfcdcd;
          height: 36px;
          line-height: 36px;
          width: 100%;

          &.error {
            border-color: #cf4444;
          }

          &.active,
          &:focus {
            border-color: #27ba9b;
          }
        }

        .code {
          position: absolute;
          right: 1px;
          top: 1px;
          text-align: center;
          line-height: 34px;
          font-size: 14px;
          background: #f5f5f5;
          color: #666;
          width: 90px;
          height: 34px;
          cursor: pointer;
        }
      }

      >.error {
        position: absolute;
        font-size: 12px;
        line-height: 28px;
        color: #cf4444;

        i {
          font-size: 14px;
          margin-right: 2px;
        }
      }
    }

    .agree {
      a {
        color: #069;
      }
    }

    .btn {
      display: block;
      width: 100%;
      height: 40px;
      color: #fff;
      text-align: center;
      line-height: 40px;
      background: #27ba9b;

      &.disabled {
        background: #cfcdcd;
      }
    }
  }

  .action {
    padding: 20px 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .url {
      a {
        color: #999;
        margin-left: 10px;
      }
    }
  }
}

.subBtn {
  background: #27ba9b;
  width: 100%;
  color: #fff;
}
.tip{
  width: 100%;
  height: 20px;
  /*background-color: red;*/
  display: flex;
  color: black;
  font-size: 13px;
  cursor: pointer;
  justify-content: space-between;
}

/* 宽、高分别为垂直滚动条、水平滚动条的尺寸 */
::-webkit-scrollbar {
  width: 10px;
  height: 10px;
}

/* 滚动条轨道 */
::-webkit-scrollbar-track {
  background-color: #f5f5f5;
}

/* 滚动条滑块 */
::-webkit-scrollbar-thumb {
  /*background-color: #000000;*/
  background-image: linear-gradient(to bottom right, rgb(114, 135, 254), rgb(130, 88, 186));
}

/* 鼠标悬停在滚动条上的滑块 */
::-webkit-scrollbar-thumb:hover {
  background-color: #555;
}

</style>

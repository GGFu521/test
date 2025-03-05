<template>
  <div>
    <header class="login-header">
      <div class="container2 m-top-20">
        <h1 class="logo">
          <RouterLink to="/">农场环境监控系统</RouterLink>
        </h1>
<!--        <RouterLink class="entry" to="/register">-->
<!--          注册账号-->
<!--          <i class="el-icon-arrow-right"></i>-->
<!--          <i class="el-icon-arrow-right"></i>-->
<!--        </RouterLink>-->
      </div>
    </header>
    <section class="login-section">
      <div class="login-wrapper">
        <nav>
          <a href="javascript:;">账户登录</a>
        </nav>
        <div class="account-box">
          <div class="form">
            <el-form ref="loginRef" :model="form" :rules="rules" label-position="right" label-width="60px" status-icon>
              <el-form-item prop="userName" label="账户">
                <el-input  v-model="form.userName" />
              </el-form-item>
              <el-form-item prop="password" label="密码">
                <el-input v-model="form.password" />
              </el-form-item>
              <el-form-item prop="agree" label-width="22px">
                <div class="tip">
                  <el-checkbox v-model="checked">记住我</el-checkbox>
                  <el-button type="primary" @click="toRegister">注册</el-button>
                </div>
              </el-form-item>
              <el-form-item label="用户类型" prop="userType">
                <el-radio-group v-model="form.userType">
                  <el-radio :label="1">管理员</el-radio>
                  <el-radio :label="2" style="margin-left: 20px">普通用户</el-radio>
<!--                  <el-radio :label="3">学生</el-radio>-->
                </el-radio-group>
              </el-form-item>

              <el-button size="large" class="subBtn" @click="login">点击登录</el-button>
            </el-form>
          </div>
        </div>
      </div>
    </section>

  </div>
</template>

<script setup>
    import { validateEmail } from '@/utils/validator';
    import {ref,onMounted,getCurrentInstance} from 'vue';
    import { useRouter} from 'vue-router'
    import { ElMessageBox } from 'element-plus'
    const { proxy } = getCurrentInstance();
    const router = useRouter();
    import {useUserStore} from '@/stores';
    const userStore = useUserStore();
    const rules = ref({
      userName: [{required: true, message: '学号不得为空', trigger: "blur"}],
      password: [{required: true, message: '密码不得为空', trigger: "blur"}],
    });



    //数据层
    const validatePass = (rule, value, callback) => {
        if (value === '') {
            callback(new Error('请输入密码'));
        } else {
            if (form.value.confirmPassword !== '') {
                resetRef.value.validateField('confirmPassword');
            }
            callback();
        }
    };
    const validatePassAgain = (rule, value, callback) => {
        if (value === '') {
            callback(new Error('请再次输入密码'));
        } else if (value !== form.value.newPassword) {
            callback(new Error('两次输入密码不一致!'));
        } else {
            callback();
        }
    };

    const url = new URL('../assets/image/login-three-avatar.png',import.meta.url);
    const resetRef = ref(null);
    const loginRef = ref(null);
    const form = ref({
      userName:'',
      password:''
    });
    let checked = ref(false);
    let isSend = ref(false);
    let addOrUpdateVisible = ref(false);
    const resetRule = ref({
      newPassword: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
        { validator: validatePass, trigger: 'blur' }
      ],
      confirmPassword:[
        { required: true, message: '请确认密码', trigger: 'blur' },
        { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
        { validator: validatePassAgain, trigger: 'blur', required: true }
      ],
      code: [{required: true, message: '验证码不得为空', trigger: "blur"}],
      email: [{required: true, message: '邮箱不得为空', trigger: "blur"},{validator:validateEmail,trigger: 'blur'}],
    });

    onMounted(() =>{
        getCookie();
      window.addEventListener('keydown', handleKeyDown);
    });

    const  handleKeyDown = (event) =>{
      console.log(event.key)
      if (event.key === 'Enter') {
        // 处理回车键按下事件
        login()
      }
    };

    const login = () => {
        loginRef.value.validate(async valid => {
            if (valid) {
                await userStore.webLogin(form.value);
                sessionStorage.setItem('userType',userStore.userInfo.userType);
                sessionStorage.setItem('userId',userStore.userInfo.id)
                router.push('/person');

                if (checked.value){
                    setCookie(form.value.userName,form.value.password,7);
                }else {
                    setCookie("", "", -1); // 修改2值都为空，天数为负1天就好了
                }
                proxy.$message.success('登录成功')
            }
        });
    };

    const openResetPassword = () =>{
        form.value = {};
        addOrUpdateVisible.value = true;
    };

    const saveResetPassword = () =>{
      resetRef.value.validate(valid => {
        if (valid) {
          const data = { email:form.value.email, code: form.value.code, password: form.value.newPassword };
          resetPassword(data).then(res => {
            if (res.code === 1) {
              proxy.$message.success('修改密码成功');
              addOrUpdateVisible.value = false;
            } else {
              proxy.$message.warning(res.msg)
            }
          })
        }
      });
    };


    const getAuthCode = () => {
        if (form.value.email == null || form.value.email === '') {
            return proxy.$message.warning('邮箱不得为空');
        }
        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        let isEmail = emailPattern.test(form.value.email);
        if (!isEmail) {
            return proxy.$message.warning('邮箱不合法')
        }

        ElMessageBox.alert('确定要发送邮件来获取验证码吗？', '提示', {
            type: 'warning'
        }).then(action => {
            if (action === 'confirm') {
                sendEmail({ email: form.value.email }).then(res => {
                  isSend.value = true;
                  proxy.$message.success('邮件已发送');
                  getData();

                })
            }
        }).catch(() => {});
    };

    const toRegister = () => {
        router.push('/register')
    };

    // 设置cookie
    const setCookie = (userName, password, days) => {
        let date = new Date(); // 获取时间
        date.setTime(date.getTime() + 24 * 60 * 60 * 1000 * days); // 保存的天数
        // 字符串拼接cookie
        window.document.cookie =
            "userName" + "=" + userName + ";path=/;expires=" + date.toGMTString();
        window.document.cookie =
            "password" + "=" + password + ";path=/;expires=" + date.toGMTString();
    };

    // 读取cookie 将学号和密码回显到input框中
    const getCookie = () => {
        if (document.cookie.length > 0) {
            let arr = document.cookie.split("; "); //分割成一个个独立的“key=value”的形式
            for (let i = 0; i < arr.length; i++) {
                let arr2 = arr[i].split("="); // 再次切割，arr2[0]为key值，arr2[1]为对应的value
                if (arr2[0] === "userName") {
                    form.value.userName = arr2[1];
                } else if (arr2[0] === "password") {
                    form.value.password = arr2[1];// base64解密
                    checked.value = true;
                }
            }
        }
    }

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
    width: 400px;
    /*height: 200px;*/
    line-height: 120px;

    a {
      height: 232px;
      width: 100%;
      text-indent: -9999px;
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
  height: 488px;
  position: relative;

  .login-wrapper {
    width: 380px;
    background: #fff;
    position: absolute;
    left: 50%;
    top: 54px;
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
</style>

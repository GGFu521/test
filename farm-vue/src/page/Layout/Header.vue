<template>
    <div class="header">
        <!-- 折叠按钮 -->

        <div class="header-logo" >
          <div class="collapse-btn" @click="collapseChage">
            <el-icon v-if="!collapse"><Fold /></el-icon>
            <el-icon v-else><Expand /></el-icon>
          </div>
          <div class="title">
            农场环境监控系统
          </div>
          <div class="logout">
            <div class="tip">
              <el-dropdown placement="bottom-start" class="tip" @command="handleCommand">
                <el-image class="image" :src="form.imageUrl" />
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item>{{form.realName}}</el-dropdown-item>
                    <el-dropdown-item  command="logout">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>

          </div>
        </div>



    </div>
</template>
<script setup>
import bus from '../Layout/bus';
import {ref,onMounted} from 'vue'
import {useRoute,useRouter} from 'vue-router';
import {getUserInfo} from "../../api/user";

//数据层
const form = ref({});
const addVisible = ref(false);
const collapse = ref(false);
const fullscreen = ref(false);

const route = useRoute();
const router = useRouter();
import {useUserStore} from "../../stores";
const useStore = useUserStore();
import {
    Fold,
    Expand
} from '@element-plus/icons-vue'

onMounted(() =>{
    getData();
});

const getData = () =>{
  getUserInfo({id:useStore.userInfo.id}).then(res =>{
      form.value = res.data;
  })
};

const handleCommand = (command) =>{
  if (command === 'logout') {
    sessionStorage.clear();
    router.push('/login');
  }
};


const collapseChage = () => {
    collapse.value = !collapse.value;
    bus.emit('collapse', collapse.value);
};

const handleFullScreen = () => {
    let element = document.documentElement;
    if (fullscreen.value) {
        if (document.exitFullscreen) {
            document.exitFullscreen();
        } else if (document.webkitCancelFullScreen) {
            document.webkitCancelFullScreen();
        } else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
        } else if (document.msExitFullscreen) {
            document.msExitFullscreen();
        }
    } else {
        if (element.requestFullscreen) {
            element.requestFullscreen();
        } else if (element.webkitRequestFullScreen) {
            element.webkitRequestFullScreen();
        } else if (element.mozRequestFullScreen) {
            element.mozRequestFullScreen();
        } else if (element.msRequestFullscreen) {
            // IE11
            element.msRequestFullscreen();
        }
    }
    fullscreen.value = !fullscreen.value;
};

onMounted(() =>{
    if (document.body.clientWidth < 1500) {
        collapseChage();
    }
});


</script>
<style scoped>
.header {
    position: relative;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    font-size: 22px;
    color: #fff;
    background:#324157;

  .header-logo{
    width: 100%;
    height: 100%;
    display: inline-flex;
    .collapse-btn {
      padding: 0 21px;
      cursor: pointer;
      line-height: 70px;
      flex: 1;
      text-align: center;
    }
    .title{
      flex:40;
      line-height: 70px;
    }
    .logout{
      flex: 4;
      line-height: 70px;
      position: relative;
      .tip{
        position: absolute;
        width: 90px;
        height: 50px;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
        .image{
          width: 50px;
          height: 50px;
          border-radius:50%;
        }
      }
    }
  }
}

.header .logo {
    float: left;
    width: 280px;

    line-height: 70px;
}
.header-right {
    float: right;
    padding-right: 50px;
}
.header-user-con {
    display: flex;
    height: 70px;
    align-items: center;
}
.btn-fullscreen {
    transform: rotate(45deg);
    margin-right: 5px;
    font-size: 24px;
}
.btn-bell,
.btn-fullscreen {
    position: relative;
    width: 30px;
    height: 30px;
    text-align: center;
    border-radius: 15px;
    cursor: pointer;
}
.btn-bell-badge {
    position: absolute;
    right: 0;
    top: -2px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
    background: #f56c6c;
    color: #fff;
}
.btn-bell .el-icon-bell {
    color: #fff;
}
.user-name {
    margin-left: 10px;
}
.user-avator {
    margin-left: 20px;
}
.user-avator img {
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
}
.el-dropdown-link {
    color: #fff;
    cursor: pointer;
}
.el-dropdown-menu__item {
    text-align: center;
}
.sidebar-title {
  display: inline-block;
  margin-left: 10px;
  color: #435ebe;
  font-weight: 600;
  line-height: 50px;
  font-size: 24px;
  font-family: Avenir,Helvetica Neue,Arial,Helvetica,sans-serif;
  vertical-align: middle;
}

</style>

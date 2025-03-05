<template>
  <div class="sidebar">

    <el-menu
         class="sidebar-el-menu"
        :default-active="onRoutes"
        :collapse="collapse"
        background-color="#324157"
        text-color="#bfcbd9"
        active-text-color="#20a0ff"
        unique-opened
        :default-openeds="['/te']"
        router
    >
      <template v-for="item in menuList">
        <template v-if="item.subs">
          <!--                    一级菜单-->
          <el-sub-menu :index="item.index" :key="item.index">
            <template #title>
              <!--                            <i :class="item.icon"></i>-->

              <el-icon>
                <component  :is="item.icon"></component>
              </el-icon>
              <span slot="title">{{item.title}}</span>
            </template>
            <template v-for="subItem in item.subs">
              <el-sub-menu
                  v-if="subItem.subs"
                  :index="subItem.index"
                  :key="subItem.index"
              >
                <template #title>
                  <!--                                    <i :class="subItem.icon"></i>-->
                  <el-icon>
                    <component  :is="subItem.icon"></component>
                  </el-icon>
                  {{ subItem.title }}</template>
                <!--                                二级菜单-->
                <el-menu-item
                    v-for="(threeItem,i) in subItem.subs"
                    :key="i"
                    :index="threeItem.index"
                > <el-icon>
                  <component  :is="threeItem.icon"></component>
                </el-icon>
                  {{ threeItem.title }}</el-menu-item>
              </el-sub-menu>
              <el-menu-item
                  v-else
                  :index="subItem.index"
                  :key="subItem"
              >
                <el-icon>
                  <component  :is="subItem.icon"></component>
                </el-icon>
                {{ subItem.title }}</el-menu-item>
            </template>
          </el-sub-menu>

        </template>
        <template v-else>
          <el-menu-item :index="item.index" :key="item.index">
            <el-icon>
              <component  :is="item.icon"></component>
            </el-icon>
            <span slot="title">{{ item.title }}</span>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script setup>
import bus from '../Layout/bus';
import {ref,computed,onMounted,shallowRef} from 'vue'
import { useRouter,useRoute } from 'vue-router'
import {useUserStore} from "@/stores";
import {Document, Menu as IconMenu, Location, Setting, Loading, Plus, Odometer, User, House,Position} from '@element-plus/icons-vue'
const userStore = useUserStore();
const router = useRouter();
const route = useRoute();
const collapse = ref(false);
const arr = [];
const menuList = ref([]);
const itemList1 = shallowRef(
    [{
  "id":4,
  "pid":1,
  "icon":Loading,
  "index":"/te",
   "title": "统一管理",
  "subs":[
    {
      "id":9,
      "pid":4,
      "icon":User,
      "index":"/person",
      "title":"个人管理",
      "subs":null
    },
    {
        "id":9,
        "pid":4,
        "icon":Position,
        "index":"user",
        "title":"用户管理",
        "subs":null
    },
    {
        "id":9,
        "pid":4,
        "icon":Position,
        "index":"place",
        "title":"农场管理",
        "subs":null
    },
    {
        "id":9,
        "pid":4,
        "icon":Position,
        "index":"setting",
        "title":"设置管理",
        "subs":null
    },
    {
        "id":9,
        "pid":4,
        "icon":Position,
        "index":"environment",
        "title":"环境管理",
        "subs":null
    },
  ]
}]);
const itemList2 = shallowRef(
    [{
  "id":4,
  "pid":1,
  "icon":Loading,
  "index":"/te",
   "title": "普通用户管理",
  "subs":[
    {
      "id":9,
      "pid":4,
      "icon":User,
      "index":"/person",
      "title":"个人管理",
      "subs":null
    },
    {
        "id":9,
        "pid":4,
        "icon":Position,
        "index":"place",
        "title":"农场管理",
        "subs":null
    },
    {
        "id":9,
        "pid":4,
        "icon":Position,
        "index":"environment",
        "title":"环境管理",
        "subs":null
    },
  ]
}]);

const onRoutes = computed(() =>{
  return route.path && route.path.replace('/', '')
});

onMounted(() =>{
  // 通过 Event Bus 进行组件间通信，来折叠侧边栏
  bus.on('collapse', msg => {
    collapse.value = msg;
    bus.emit('collapse-content', msg);
  });
  let userType = userStore.userInfo.userType;
  if (userType === 1){
    menuList.value = itemList1.value;
  }
  if (userType === 2){
    menuList.value = itemList2.value;
  }

})

</script>

<style scoped>
.sidebar {
  display: block;
  position: absolute;
  left: 0;
  top: 70px;
  bottom: 0;
  overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
  width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
  width: 250px;
}
.sidebar > ul {
  height: 100%;
}

:deep(.el-menu-item:hover){
  outline: 0!important;
  color: #fff!important;
  background: #3e9eff!important;
  border-radius: 8px!important;
}
</style>

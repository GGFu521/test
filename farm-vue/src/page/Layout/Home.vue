、<template>
    <div class="wrapper">
        <v-head></v-head>
        <v-sidebar></v-sidebar>
        <div class="content-box" :class="{'content-collapse':collapse}">
            <v-tags></v-tags>
            <div class="content">
<!--                <transition name="move" mode="out-in">-->
                    <keep-alive :include="tagsList">
                        <router-view></router-view>
                    </keep-alive>
<!--                </transition>-->
                <el-backtop target=".content"></el-backtop>
            </div>
        </div>
    </div>
</template>

<script setup>
    import vHead from './Header.vue';
    import vSidebar from './Sidebar.vue';
    import vTags from './Tags.vue';
    import bus from './bus';
    import {ref,onMounted} from 'vue';
    import {createConnection, doSubscribe} from "@/utils/mqtt";


    const tagsList = ref([]);
    const collapse = ref(false);

    onMounted(() =>{
        bus.on('collapse-content', msg => {
            collapse.value = msg;
        });

        // 只有在标签页列表里的页面才使用keep-alive，即关闭标签之后就不保存到内存中了。
        bus.on('tags', msg => {
            let arr = [];
            for (let i = 0, len = msg.length; i < len; i++) {
                msg[i].name && arr.push(msg[i].name);
            }
            tagsList.value = arr;
        });
        // console.log(tagsList.value)

      createConnection();
      doSubscribe("BACK/SRV000/EV",0);
    })


</script>

<style scoped>
    @import "../Layout/css/main.css";
</style>

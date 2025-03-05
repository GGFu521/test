import {defineStore} from 'pinia'
import {ref} from 'vue'
import {Login,getUserInfo} from "@/api/user";

export const useUserStore = defineStore('user',() =>{

    const userInfo = ref(null);

    const client = ref({})

    //登录获取信息
    const webLogin = async (form) =>{
        const res = await Login(form);
        userInfo.value = res.data;
    };

    //获取个人信息
    const webGetUserInfo = async (id) =>{
        const res = await getUserInfo(id);
        userInfo.value = res.data;
    };

    const setMqttClient = async (client) =>{
        client.value = client;
    };


    //清除用户信息
    const clearUserInfo = () =>{
        userInfo.value = null;
    };

    return{
        webLogin,
        webGetUserInfo,
        clearUserInfo,
        userInfo
    }
},{
    persist:{
        enable:true,
        storage: sessionStorage
    },

});

import { createRouter, createWebHistory } from 'vue-router'
import {useUserStore} from "../stores/index";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        // {
        //     path: '/',
        //     redirect: '/Home'
        // },
        {
            path: '/Home',
            component: () => import(/* webpackChunkName: "home" */ '../page/Layout/Home.vue'),
            meta: { title: '首页' },
            children: [
                {
                    path: '/person',
                    component: () => import(/* webpackChunkName: "icon" */ '../page/Person.vue'),
                    meta: { title: '个人信息' }
                },
                {
                    path: '/environment',
                        component: () => import(/* webpackChunkName: "icon" */ '../page/Environment.vue'),
                    meta: { title: '环境信息' }
                },
                {
                    path: '/place',
                        component: () => import(/* webpackChunkName: "icon" */ '../page/Place.vue'),
                    meta: { title: '农场信息' }
                },
                {
                    path: '/setting',
                        component: () => import(/* webpackChunkName: "icon" */ '../page/Setting.vue'),
                    meta: { title: '设置信息' }
                },
                {
                    path: '/user',
                        component: () => import(/* webpackChunkName: "icon" */ '../page/User.vue'),
                    meta: { title: '用户信息' }
                },
                {
                    path: '/404',
                    component: () => import(/* webpackChunkName: "404" */ '../page/404.vue'),
                    meta: { title: '404' }
                },
            ]
        },

        {
            path: '/:pathMatch(.*)*',
            redirect: '/404'
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../page/Login.vue'),
            meta: { title: '登录' }
        },
        {
            path: '/register',
            component: () => import(/* webpackChunkName: "login" */ '../page/Register.vue'),
            meta: { title: '注册' }
        },
    ],
//路由滚动行为定制(切换页面后可以直接返回顶部)
    scrollBehavior(){
        return{
            top:0
        }
    }
});
router.beforeEach((to,from,next) =>{
    if (to.path === '/login' || to.path === '/register') {
        next()
    }else{
        const userStore = useUserStore();
        const token = userStore.userInfo;
        if (!token){
            next('/login')
        }else{
            next();
        }
    }
});


export default router

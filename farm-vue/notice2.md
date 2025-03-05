##路由信息
{
    path: '/index-notice',
    component: () => import(/* webpackChunkName: "dashboard" */ '../views/IndexNotice.vue'),
    meta: { title: '公告管理' ,hasPerms: false},
    hasPerms: false,
},
{
    path: '/notice-detail',
    component: () => import(/* webpackChunkName: "dashboard" */ '../views/NoticeDetail.vue'),
    meta: { title: '公告详情' ,hasPerms: false},
    hasPerms: false,
},

##侧边栏

{
    "id":9,
    "pid":4,
    "menuIcon":"el-icon-plus",
    "path":"notice",
    "menuName":"行业资讯",
    "children":[]
},
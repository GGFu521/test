
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import { createApp } from 'vue'
import ElementPlus from 'element-plus'

import 'element-plus/dist/index.css'
import zhCN from "element-plus/dist/locale/zh-cn.mjs";

import App from './App.vue'
import router from './router'
import '@/utils/flex'
//导入阿里云图标
import '@/assets/fonts/iconfont.css'
// 引入icon
import * as ElIcons from '@element-plus/icons-vue'
const app = createApp(App);
//注册icon
for (const name in ElIcons){
    app.component(name,(ElIcons)[name])
}

// 导入视频播放组件
import VueVideoPlayer from '@videojs-player/vue'
import 'video.js/dist/video-js.css'


// 视频播放组件
app.use(VueVideoPlayer)

import SvgIcon from './components/icon-component.vue';
// import 'virtual:svg-icons-register';
app.component('svg-icon', SvgIcon);

const pinia = createPinia();
// 注册持久化插件
pinia.use(piniaPluginPersistedstate);
app.use(ElementPlus,{
    size:'small',
    locale: zhCN
});
app.use(pinia);
app.use(router);


app.mount('#app');



//markDown编译器
import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
import Prism from 'prismjs';
VueMarkdownEditor.use(vuepressTheme, {
    Prism,
});
app.use(VueMarkdownEditor);
//预览
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
import hljs from 'highlight.js';
VMdPreview.use(githubTheme, {
    Hljs: hljs,
});
app.use(VMdPreview);

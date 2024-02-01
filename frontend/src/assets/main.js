import '@babel/polyfill'
import 'mutationobserver-shim'
import './plugins/bootstrap-vue'
import { createApp } from 'vue';
import './assets/common.css'
import store from "@/scripts/store";
import App from './App.vue';
import router from './scripts/router';


createApp(App).use(store).use(router).mount('#app')

import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js';
import './assets/common.css'
import store from "@/vuex/store.js";
// import { BootstrapVue } from 'bootstrap-vue'

// Import Bootstrap and BootstrapVue CSS files (order is important)
// import 'bootstrap/dist/css/bootstrap.css'
// import 'bootstrap-vue/dist/bootstrap-vue.css'


createApp(App).use(store).use(router)
// .use(BootstrapVue)
.mount('#app')

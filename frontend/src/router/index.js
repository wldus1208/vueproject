import { createRouter, createWebHistory } from 'vue-router';

import HomePage from '@/pages/HomePage.vue';
import Banner from '@/pages/BannerPage.vue';

import Login from "@/pages/member/LoginPage.vue";
import Sign from "@/pages/member/SignPage";

import User from "@/pages/admin/ListPage";
import Admin from "@/pages/admin/AdminCreate";

import BoardList from "@/pages/board/BoardList.vue";
import BoardDetail from "@/pages/board/BoardDetail.vue";
import BoardWrite from "@/pages/board/BoardWrite.vue";

import Map from "@/pages/map/MainMapApp.vue";

const routes = [
    {path: "/", component: Banner},
    {path: "/login", component: Login},
    {path: "/sign", component: Sign},
    {path: "/user", component: User},
    {path: "/board/list", component: BoardList},
    {path: "/board/detail/:id", component: BoardDetail},
    {path: "/board/write", component: BoardWrite},
    {path: "/movie", component: HomePage},
    {path: "/map", component: Map},
    {path: "/admin", component: Admin},
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;
// router/index.js
import { createRouter, createWebHistory } from "vue-router";
import MainBody from "@/components/MainBody.vue";
import PostDetail from "@/components/PostDetail.vue";
import ArticleManagement from "@/components/ArticleManagement.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: MainBody,
  },
  {
    path: "/articles/:id",
    name: "PostDetail",
    component: PostDetail,
    props: true,
  },
  {
    path: "/ArticleManagement",
    name: "ArticleManagement",
    component: ArticleManagement,
    props: true,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

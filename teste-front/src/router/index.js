import LoginView from "@/views/LoginView.vue";
import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    name: "login",
    component: LoginView,
  },
  {
   path: "/Sucesso",
   name: "sucesso",
   component: () =>
    import(/* webpackChunkName: "about" */ "../views/SucessoView.vue"),
  },
  {
    path:"/mudarSenha",
    name: "reset senha",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/MudarSenhaView.vue"),
  },
];

const router = createRouter({
 history: createWebHistory(process.env.BASE_URL),
 routes,
});

export default router;

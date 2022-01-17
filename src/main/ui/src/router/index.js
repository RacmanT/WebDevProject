import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Auth from "../views/Auth.vue";
import AddTrip from "@/views/AddTrip.vue";


Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
    meta: { requiresAuth: true },
  },
  {
    path: "/add",
    name: "Add trip",
    component: AddTrip,
    meta: { requiresAuth: true },
  },
  {
    path: "/view",
    name: "View trip",
    component: () => import("../views/ViewTrip.vue"),
    meta: { requiresAuth: true },
    props: true
  },
  {
    path: "/signup",
    name: "Sign up",
    component: () => import("../views/SignUp.vue"),
  },

  {
    path: "/signin",
    name: "Sign in",
    component: () => import("../views/SignIn.vue"),
  },

  {
    path: "/auth",
    name: "Authentication",
    component: Auth,
    meta: { requiresAuth: false },
  },
  {
    path: "*",
    name: "NotFound",
    component: () => import("../views/NotFound.vue"),
  },
];

const router = new VueRouter({
  routes,
});

const user = { isAuthenticated: true };

router.beforeEach((to, from, next) => {
  if (to.name === "NotFound") next();
  else if (to.meta.requiresAuth && !user.isAuthenticated)
    next({ name: "Authentication" });
  else if (!to.meta.requiresAuth && user.isAuthenticated)
    next({ name: "Home" });
  else next();
});

export default router;

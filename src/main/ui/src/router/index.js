import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Auth from "../views/Auth.vue";

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
    component: () => import("../views/AddTrip.vue"),
    meta: { requiresAuth: true },
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
];

const router = new VueRouter({
  routes,
});

const user = { isAuthenticated: true };

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !user.isAuthenticated) next({ name: "Authentication" });
  else if (!to.meta.requiresAuth && user.isAuthenticated) next({ name: "Home" });
  else next()
})

export default router;

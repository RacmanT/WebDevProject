import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Auth from "../views/Auth.vue";
import AddTrip from "../views/AddTrip.vue";

import firebase from "firebase/compat/app";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
    meta: { requiresAuth: true },
  },
  {
    path: "/",
    name: "Add trip",
    component: AddTrip,
    meta: { requiresAuth: true },
  },
  {
    path: "/",
    name: "View trip",
    component: () => import("../views/ViewTrip.vue"),
    meta: { requiresAuth: true },
  },

  {
    path: "/",
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

// empty catch due to vue router bug : https://stackoverflow.com/a/65326844
router.beforeEach((to, from, next) => {
  if (to.name === "NotFound") next();
  else if (to.meta.requiresAuth && !firebase.auth().currentUser)
    router.push({ name: "Authentication" }).catch(() => {});
  else if (!to.meta.requiresAuth && firebase.auth().currentUser)
    router.push({ name: "Home" }).catch(() => {});
  else next();
});


export default router;

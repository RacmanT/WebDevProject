import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Auth from "../views/Auth.vue";
import AddTrip from "@/views/AddTrip.vue";

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

router.beforeEach((to, from, next) => {
  if (to.name === "NotFound") next();
  else if (to.meta.requiresAuth && !firebase.auth().currentUser)
    router.push({ name: "Authentication" }).catch(() => {});
  else if (!to.meta.requiresAuth && firebase.auth().currentUser)
    router.push({ name: "Home" }).catch(() => {});
  else next();
});

/* router.beforeEach((to, from, next) => {
  firebase.auth().onAuthStateChanged((user) => {
    if (to.name === "NotFound") next();
    else if (to.meta.requiresAuth && !user) router.push({ name: "Authentication" }).catch(err => err);
    else if (!to.meta.requiresAuth && user) router.push({ name: "Home" }).catch(err => err);
    else next();
  });
}); */

/* router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    firebase.auth().onAuthStateChanged((user) => {
      if (!user) {
        next({ name: "Authentication" });
      } else {
        next();
      }
    });
  } else {
    next();
  }
}); */

export default router;

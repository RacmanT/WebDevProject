import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/signup',
    name: 'Sign up',
    component: () => import('../views/SignUp.vue')
  },

  {
    path: '/signin',
    name: 'Sign in',
    component: () => import('../views/SignIn.vue')
  },

  {
    path: '/auth',
    name: 'Authentication',
    component: () => import('../views/Auth.vue')
  },
  {
    path: '/add',
    name: 'Add trip',
    component: () => import('../views/AddTrip.vue')
  },


]

const router = new VueRouter({
  routes
})

export default router

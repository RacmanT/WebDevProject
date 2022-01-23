import "@babel/polyfill";
import "mutationobserver-shim";
import Vue from "vue";
import "./plugins/bootstrap-vue";
import App from "./App.vue";
import router from "./router";
import "bootswatch/dist/litera/bootstrap.min.css";
import "leaflet/dist/leaflet.css";
import "@fortawesome/fontawesome-free/css/all.css";
import "@fortawesome/fontawesome-free/js/all.js";
import L from "leaflet";
import { BootstrapVueIcons } from "bootstrap-vue";
import "bootstrap-vue/dist/bootstrap-vue-icons.min.css";
import store from "./store";
import firebase from 'firebase/compat/app';
import 'firebase/compat/auth';//
import 'firebase/compat/firestore';//

// https://github.com/Leaflet/Leaflet/issues/4968
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
  iconUrl: require("leaflet/dist/images/marker-icon.png"),
  shadowUrl: require("leaflet/dist/images/marker-shadow.png"),
});

Vue.config.productionTip = false;

const firebaseConfig = {
  apiKey: "AIzaSyDFTDljeRfq1HgZC5LxgFSX1bZwLG2DvzQ",
  authDomain: "webdev2021-61701.firebaseapp.com",
  projectId: "webdev2021-61701",
  storageBucket: "webdev2021-61701.appspot.com",
  messagingSenderId: "982645710338",
  appId: "1:982645710338:web:f3ce18bb8d2635d324511b"
};



firebase.initializeApp(firebaseConfig);


let app;
firebase.auth().onAuthStateChanged(() => {
  if (!app) {
    app = new Vue({
      router,
      store,
      render: h => h(App)
    }).$mount('#app')
  }
})



/* new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app"); */

Vue.use(BootstrapVueIcons);

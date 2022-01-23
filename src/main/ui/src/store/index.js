import Vue from "vue";
import Vuex from "vuex";

import trips from "./modules/trips";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    trips,
  },
  plugins: [createPersistedState()],
});

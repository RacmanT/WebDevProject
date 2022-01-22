import { isEqual, omit } from "lodash";
import firebase from "firebase/compat/app";

const state = {
  trips: [],
  targetTrip: {},
  tripTable: [],
};

const getters = {
  allTrips: (state) => state.trips,
  filteredTrips: (state) => (date) => {
    return state.trips.filter((trip) => trip.date === date);
  },
  selectedTrip: (state) => state.targetTrip,
  tripTable: (state) => {
    return state.targetTrip !== null
      ? state.targetTrip.path
          .filter((location) => location.important)
          .map((location) => omit(location, ["important"]))
      : null;
  },
};

const actions = {
  async fetchTrips({ commit }) {
    const token = await firebase.auth().currentUser.getIdToken();
    //const response = await fetch(`${process.env.VUE_APP_REST_URL}/trip`);
    console.log(`header :   Authorization: Bearer ${token}`);

    const response = await fetch(`${process.env.VUE_APP_REST_URL}/trip`, {
      method: "GET",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });

    const trips = await response.json();
    commit("setTrips", trips); //commit('setTrips', response.data)
  },

  async addTrip({ commit }, trip) {
    const token = await firebase.auth().currentUser.getIdToken();
    const response = await fetch(`${process.env.VUE_APP_REST_URL}/trip`, {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(trip),
    });
    const tripResponse = await response.json();
    commit("newTrip", tripResponse);
  },

  async updateTrip({ commit }, trip) {
    const token = await firebase.auth().currentUser.getIdToken();
    const response = await fetch(`${process.env.VUE_APP_REST_URL}/trip`, {
      method: "PUT",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(trip),
    });
    const tripResponse = await response.json();
    commit("updateTrip", tripResponse);
  },

  async deleteTrip({ commit }, trip) {
    const token = await firebase.auth().currentUser.getIdToken();
    const response = await fetch(`${process.env.VUE_APP_REST_URL}/trip`, {
      method: "DELETE",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(trip),
    });
    const tripResponse = await response.json();
    commit("removeTrip", tripResponse);
  },
};

const mutations = {
  setTrips: (state, trips) => (state.trips = trips),
  newTrip: (state, trip) => state.trips.push(trip),
  updateTrip: (state, trip) => {
    const index = state.trips.findIndex((targetTrip) =>
      isEqual(targetTrip, trip)
    );
    if (index !== -1) state.trips.splice(index, 1, trip);
  },
  removeTrip: (state, trip) =>
    (state.trips = state.trips.filter(
      (tripToDelete) => trip.id !== tripToDelete.id
    )),

  setTargetTrip: (state, trip) => (state.targetTrip = trip),
};

export default {
  state,
  getters,
  actions,
  mutations,
};

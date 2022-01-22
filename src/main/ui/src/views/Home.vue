<template>
  <b-container
  class="my-4"
  >
    <h1 class="my-4">Hi {{username}}</h1>
    <b-form-datepicker
      v-model="date"
      class="mb-2"
      menu-class="w-100"
      calendar-width="100%"
      :date-info-fn="dateClass"
      b-form-datepicker
    />

    <b-list-group v-for="trip in filteredTrips(date)" v-bind:key="trip.id">
      <b-list-group-item button @click.prevent="viewTrip(trip)">{{
        trip.date
      }}</b-list-group-item>
    </b-list-group>


    <b-button
      variant="outline-success"
      class="mt-4"
      @click.prevent="addNewTrip()"
      >Add trip
    </b-button>
  </b-container>
</template>

<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
import firebase from "firebase/compat/app";

export default {
  data() {
    return { date: null, username: "" };
  },

  methods: {
    ...mapActions(["fetchTrips"]),
    ...mapMutations(["setTargetTrip"]),

    addNewTrip() {
      this.setTargetTrip({ date: this.date , vehicle: "walk", path:[]});
      this.$router.push({ name: "Add trip" });
    },

    viewTrip(trip) {
      this.setTargetTrip(trip);
      this.$router.push({ name: "View trip" });
    },
    dateClass(ymd, date) {
      return this.allTrips
        .map((trip) => new Date(trip.date))
        .some((targetDate) => {
          return targetDate.toDateString() === date.toDateString();
        })
        ? ""
        : "table-active";
    },
  },

  computed: mapGetters(["allTrips", "filteredTrips", "selectedTrip"]),
  async created() {
    await this.fetchTrips();
    this.date = this.selectedTrip === null? this.allTrips.at(-1).date : this.selectedTrip.date
    this.username =  firebase.auth().currentUser.displayName;

    const token = await firebase.auth().currentUser.getIdToken();
    console.log(token)

  
  },
};
</script>

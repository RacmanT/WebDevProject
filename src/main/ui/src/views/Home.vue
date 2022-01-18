<template>



  <b-container >
    <h1 class="my-4">Welcome</h1>
    <b-form-datepicker
      v-model="date"
      class="mb-2"
      menu-class="w-100"
      calendar-width="100%"
      :date-info-fn="dateClass"

    b-form-datepicker/>

    

     <b-list-group v-for="trip in filteredDates" v-bind:key="trip.id">
      <b-list-group-item
        button
        @click.prevent="goToRouter(trip)"
        >{{ trip.date }}</b-list-group-item
      >
    </b-list-group> 

    <b-button
      variant="outline-success"
      class="mt-4"
      @click="$router.push({ name: 'Add trip' })"
      >Add trip
    </b-button>

    <!-- <Map :geojson="geojson" />
    <b-table hover :items="items"></b-table> -->

    <!-- <b-list-group>
      <b-list-group-item v-b-toggle.collapse-3
        >Cras justo odio</b-list-group-item
      >
      <b-collapse id="collapse-3">
        <b-card>I should start open!</b-card>
      </b-collapse>
    </b-list-group> -->
  </b-container>

</template>

<script>
// import Map from "@/components/Map.vue";
import { isEmpty } from "lodash";

export default {
  /* components: {
    Map,
  }, */
  data() {
    return {
      date: null,
      trips: [],
    };
  },
  async created() {
    const response = await fetch(`${process.env.VUE_APP_REST_URL}/trip`);
    this.trips = await response.json();
    this.date = isEmpty(this.trips) ? null :  this.trips.at(-1).date; //TODO if trip is not null?
    /* console.log(JSON.stringify(this.trips));
    console.log(
      new Date(this.date).getTime() === new Date("2009-01-11").getTime()
    ); */
  },

  methods: {
    dateClass(ymd, date) {
      return this.trips
        .map((trip) => new Date(trip.date))
        .some((targetDate) => {
          if (targetDate.toDateString() === date.toDateString()) {
            console.log(targetDate.toDateString());
          }
          return targetDate.toDateString() === date.toDateString();
        })
        ? ""
        : "table-active";
    },

    goToRouter(trip){
     
      this.$router.push({ name: 'View trip', params: trip })
    }
  },

  computed: {
    filteredDates() {
      return this.trips.filter((trip) => trip.date === this.date);
    },
  },
};
</script>

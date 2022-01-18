<template>
  <b-container
    fluid
    align-v="center"
    align-h="center"
    class="mt-5"
    style="height: 500px; width: 100%"
  >
    <h1>Trip info</h1>

    <Map :geojson="geojson" />

    <b-table :items="tripTable"></b-table>

    <div class="float-left mt-3" style="height: 50%; width: 50%">
      <b-button variant="primary" @click="$router.go(-1)">Back </b-button>
    </div>

    <div class="float-right mt-3">
      <b-button class="mx-2" variant="danger" @click="deleteTrip()"
        >Delete
      </b-button>
      <b-button variant="info" @click="editTrip()">Edit </b-button>
    </div>
  </b-container>
</template>

<script>
import Map from "@/components/Map.vue";
import { isEmpty, omit } from "lodash";

export default {
  components: {
    Map,
  },

  data() {
    return {
      geojson: null,
      trip: null,
    };
  },
  methods: {
    toGeoson(trip) {
      const coordinates = [];
      const geojson = {
        type: "FeatureCollection",
        features: [],
      };

      if (!isEmpty(trip)) {
        trip.path.forEach((location) => {
          coordinates.push([location.longitude, location.latitude]);
          if (location.important) {
            geojson.features.push({
              type: "Feature",
              properties: {},
              geometry: {
                type: "Point",
                coordinates: [location.longitude, location.latitude],
              },
            });
          }
        });

        geojson.features.push({
          type: "Feature",
          properties: {},
          geometry: {
            type: "LineString",
            coordinates: coordinates,
          },
        });
      }
      return geojson;
    },
  },

  computed: {
    tripTable() {
      /* let tripTable = null;
      if (this.trip) {
        tripTable = this.trip.path
          .filter((location) => location.important)
          .map((location) => omit(location, ["important"]));
      }
      return tripTable; */

      return !isEmpty(this.trip)
        ? this.trip.path
            .filter((location) => location.important)
            .map((location) => omit(location, ["important"]))
        : null;
    },
  },

  async beforeCreate() {
    const response = await fetch(`${process.env.VUE_APP_REST_URL}/trip`);
    const trips = await response.json();
    //this.trip = trips.at(0);
    console.log(trips.at(-1));
    console.log(this.trip);
    this.trip = this.$route.params;
    this.geojson = this.toGeoson(this.trip); // TODO change to trip.path
  },
};
</script>

<style>
</style>
<template>
  <b-container
    fluid
    align-v="center"
    align-h="center"
    class="mt-5"
    style="height: 500px; width: 500px"
  >
    <h1>Trip info</h1>
    <h2>Trip target is {{ tripTarget }}</h2>
    <Map :geojson="geojson" />
    <b-table :items="tripTable"></b-table>

    <div class="float-left mt-3">
      <b-button variant="primary" @click="save()">Back </b-button>
    </div>

    <div class="float-right mt-3">
      <b-button class="mx-2" variant="danger" @click="save()">Delete </b-button>
      <b-button variant="info" @click="back()">Edit </b-button>
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
  props: {
    tripTarget: null,
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
      let tripTable = null;
      if (this.trip) {
        tripTable = this.trip.path
          .filter((location) => location.important)
          .map((location) => omit(location, ["important"]));
      }
      return tripTable;
    },
  },

  async beforeCreate() {
    const response = await fetch(`${process.env.VUE_APP_REST_URL}/trip`);
    const trips = await response.json();
    this.trip = trips.at(0);
    this.geojson = this.toGeoson(this.trip); // TODO change to trip.path
    /* console.log(JSON.stringify(this.trips));
    console.log(
      new Date(this.date).getTime() === new Date("2009-01-11").getTime()
    ); */
  },
};
</script>

<style>
</style>
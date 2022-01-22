<template>
  <b-container
    fluid
    align-v="center"
    align-h="center"
    class="mt-5"
    style="height: 500px; width: 80%"
  >
    <h1>{{ this.selectedTrip.name }} {{ usedVehicle }}</h1>

    <div class="float-right mb-3">
      <b-button class="mx-2" variant="danger" @click="trashTrip()"
        ><i class="far fa-trash-alt"></i>
      </b-button>
      <b-button variant="info" @click="editTrip()"
        ><i class="fas fa-edit"></i>
      </b-button>
    </div>

    <div class="float-left">
      <b-button variant="primary" @click="$router.replace({ name: 'Home' })"
        >Back
      </b-button>
    </div>

    <Map :geojson="geojson" />

    <b-table :items="tripTable"></b-table>
  </b-container>
</template>

<script>
import Map from "@/components/Map.vue";
import { isEmpty } from "lodash";
import { mapGetters, mapActions, mapMutations } from "vuex";

export default {
  components: {
    Map,
  },

  data() {
    return {
      geojson: null,
      usedVehicle: "",
    };
  },
  methods: {
    ...mapActions(["deleteTrip"]),
    ...mapMutations(["setTargetTrip"]),
    async trashTrip() {
      if (confirm("Are you sure you want to delete this trip?")) {
        await this.deleteTrip(this.selectedTrip).then(() => {
          this.setTargetTrip({ date: this.selectedTrip.date });
          this.$router.replace({ name: "Home" });
        });
      }
    },
    editTrip() {
      this.$router.push({ name: "Add trip" });
    },
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

  computed: mapGetters(["selectedTrip", "tripTable"]),

  created() {
    if (isEmpty(this.selectedTrip)) {
      this.$router.replace({ name: "Home" });
    }
    this.usedVehicle =
      this.selectedTrip.vehicle === "walk"
        ? `by ${this.selectedTrip.vehicle}`
        : `with ${this.selectedTrip.vehicle}`;
    this.geojson = this.toGeoson(this.selectedTrip);
  },
};
</script>

<style>
</style>
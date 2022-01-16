<template>
  <b-container
    fluid
    align-v="center"
    align-h="center"
    class="mt-5"
    style="height: 500px; width: 500px"
  >
    <b-form-datepicker
      v-model="date"
      class="mb-2"
      menu-class="w-100"
      calendar-width="100%"
      :date-info-fn="dateClass"
    />

    <p>{{ date }}</p>

    <Map :geojson="geojson" />
    <b-table hover :items="items"></b-table>

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
import Map from "@/components/Map.vue";
import { isEmpty } from "lodash";

export default {
  components: {
    Map,
  },
  data() {
    return {
      date: null,
      geojson: null,
      items: [{ age: 40, first_name: "Dickerson", last_name: "Macdonald" }],
    };
  },
  async created() {
    const response = await fetch(`${process.env.VUE_APP_REST_URL}/trip`);
    const trips = await response.json();
    console.log(trips);
    this.geojson = this.toGeoson(trips.at(1));

    /*   const stringa =
      '{"id":"443c436e15a740d38839fd6d2841dec2", "date":"2009-01-11", "vehicle":"walk", "path":[{"longitude":11.5356, "latitude":12.5356, "name":"Roma", "important":false}, {"longitude":17.5356, "latitude":22.5356, "name":"Trieste", "important":true}]}';
    const trip = JSON.parse(stringa);
    this.geojson = this.toGeoson(trip);
    console.log(JSON.stringify(this.geojson)); */
  },

  methods: {
    addLine(geojson) {
      if (!isEmpty(this.geojson)) {
        let temp = [];
        geojson.features
          .filter((feature) => feature.geometry.type === "Point")
          .forEach((feature) => temp.push(feature.geometry.coordinates));

        geojson.features.at(-1).geometry.coordinates = temp;
      }
      return geojson;
    },

    dateClass(ymd, date) {
      const day = date.getDate();
      return day >= 10 && day <= 20 ? "" : "table-active";
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
};
</script>

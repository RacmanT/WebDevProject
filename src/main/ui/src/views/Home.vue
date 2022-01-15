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

    <p>{{date}}</p>

    <Map :geojson="addLine(geojson)" />
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
    this.geojson = await response.json();
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
        const day = date.getDate()
        return day >= 10 && day <= 20 ? '' : 'table-active'
      }

  },
};
</script>

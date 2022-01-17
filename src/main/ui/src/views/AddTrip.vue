<template>
  <b-container class="mt-2">
    <h1>Add a new trip</h1>

    <b-form-radio-group v-model="vehicle">
      <b-form-radio value="walk" inline>Walk</b-form-radio>
      <b-form-radio value="bycicle" inline
        ><b-icon icon="bicycle" variant="outline-danger" scale="1.3"></b-icon
      ></b-form-radio>
      <b-form-radio value="car" inline>Car</b-form-radio>
    </b-form-radio-group>

    <b-form-datepicker
      v-model="date"
      class="my-2"
      required
      menu-class="w-100"
      calendar-width="100%"
    />

    <b-table
      responsive
      borderless
      small
      class="mt-4"
      :items="items"
      :fields="fields"
    >
      <!-- <template v-slot:cell()="{ item, field: { key } }">
        <b-form-input  size="sm" v-model="item[key]" />
      </template> -->

      <template v-slot:cell(longitude)="row">
        <b-form-input
          type="number"
          step=".000001"
          size="sm"
          required
          min="0"
          v-model="row.item.longitude"
        />
      </template>

      <template v-slot:cell(latitude)="row">
        <b-form-input
          type="number"
          step=".000001"
          size="sm"
          required
          min="0"
          v-model="row.item.latitude"
        />
      </template>

      <template v-slot:cell(name)="row">
        <b-form-input
          size="sm"
          v-model="row.item.name"
          :state="nameRequired(row.item)"
        />
      </template>

      <template v-slot:cell(important)="row">
        <b-form-checkbox
          size="sm"
          v-model="row.item.important"
          button
          button-variant="outline"
        >
          <b-icon
            v-if="row.item.important"
            icon="star-fill"
            variant="warning"
            scale="1.3"
          />
          <b-icon v-else icon="star" scale="1.3" />
        </b-form-checkbox>
      </template>

      <template v-slot:cell(delete)="row">
        <b-button
          variant="outline-danger"
          size="sm"
          @click="deletePosition(row.item)"
        >
          <b-icon icon="x" variant="outline-danger" scale="1.3" />
        </b-button>
      </template>
    </b-table>
    <div class="w-100">
      <b-button
        pill
        variant="outline-success"
        @click="addRow"
        :disabled="lastRowIsEmpty"
        ><b-icon icon="plus" variant="outline-success" scale="1.3" />
      </b-button>
    </div>

    <div class="float-right mt-3">
      <b-button class="mx-2" variant="danger" @click="back()">Cancel </b-button>
      <b-button variant="success" @click="save()">Save </b-button>
    </div>
  </b-container>
</template>



<script>
import { isEqual } from "lodash";

export default {
  data() {
    return {
      vehicle: "walk",
      date: new Date().toISOString().split("T")[0],
      items: [
        { longitude: 12, latitude: 21, name: "Trieste", important: true },
        { longitude: 12, latitude: 21, name: "Bologna", important: false },
        { longitude: 12, latitude: 21, name: "Milano", important: true },
        { longitude: 12, latitude: 21, name: "New York", important: true },
      ],
      fields: [
        { key: "longitude", thStyle: { width: "20%" } },
        { key: "latitude", thStyle: { width: "20%" } },
        { key: "name", thStyle: { width: "40%" } },
        { key: "important", thStyle: { width: "10%" } },
        { key: "delete", label: "", thStyle: { width: "10%" } },
      ],
    };
  },
  methods: {
    addRow() {
      if (!this.lastRowIsEmpty) {
        this.items.push({
          longitude: 0,
          latitude: 0,
          name: "",
          important: false,
        });
      }
    },

    deletePosition(item) {
      this.items = this.items.filter(
        (targetItem) => !isEqual(targetItem, item)
      );
    },

    async save() {
      if (this.date === null) {
        alert("Date cannot be empty!");
        return;
      }
      var trip = { vehicle: this.vehicle, date: this.date, path: this.items };
      (trip = await fetch(`${process.env.VUE_APP_REST_URL}/trip`, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(trip),
      }).catch((err) => console.log(err))),
        console.log(trip);
    },

    back() {
      this.$router.go(-1);
    },

    nameRequired(item) {
      if (item.important && !item.name) return false;
    },
  },
  computed: {
    lastRowIsEmpty() {
      if (this.items.length === 0) return false;
      else
        return (
          this.items.at(-1).longitude === 0 && this.items.at(-1).latitude === 0
        );
    },
  },

  async created() {
    //const response = await fetch(`${process.env.VUE_APP_REST_URL}/trip/test`);
    /*  const response = await fetch(`${process.env.VUE_APP_REST_URL}/trip`);
    const info = await response.json();
    this.date = info.at(0).date;
    this.vehicle = info.at(0).vehicle;
    this.items = info.at(0).path; */
  },
};
</script>

<style scoped>
</style>
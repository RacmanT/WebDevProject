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

    <b-table borderless small class="mt-4" :items="items" :fields="fields">
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
          @click.prevent="items.pop(row.item)"
        >
          <b-icon icon="x" variant="outline-danger" scale="1.3" />
        </b-button>
      </template>
    </b-table>
    <b-button
      pill
      variant="outline-success"
      @click="addRow"
      :disabled="lastRowIsEmpty"
      ><b-icon icon="plus" variant="outline-success" scale="1.3" />
    </b-button>
  </b-container>
</template>



<script>
export default {
  data() {
    return {
      vehicle: "walk",
      date: null,
      items: [],
      fields: [
        { key: "longitude" },
        { key: "latitude" },
        { key: "name" },
        { key: "important" },
        { key: "delete", label: "" },
      ],
    };
  },
  methods: {
    addRow() {
      if (!this.lastRowIsEmpty) {
        this.items.push({
          longitude: 0,
          latitude: 0,
          name: null,
          important: false,
        });
      }
    },

    nameRequired(item) {
      if (item.important & !item.name) {
        return false;
      }
    },
  },
  computed: {
    lastRowIsEmpty() {
      if (this.items.length === 0) {
        return false;
      }
      return (
        this.items.at(-1).longitude === 0 || this.items.at(-1).latitude === 0
      );
    },
  },

  async created() {
    //const response = await fetch(`${process.env.VUE_APP_REST_URL}/trip/test`);
    const response = await fetch(`${process.env.VUE_APP_REST_URL}/trip`);
    const info = await response.json();
    this.date = info.at(0).date;
    this.vehicle = info.at(0).vehicle;
    this.items = info.at(0).path;
  },
};
</script>

<style scoped>

</style>
<template>
  <b-container class="mt-2">
    <h1>Add a new trip</h1>

    <b-form-radio-group v-model="vehicle">
      <b-form-radio value="walk" inline>
        <i class="fas fa-walking fa-lg"></i>
      </b-form-radio>
      <b-form-radio value="bycicle" inline
        ><i class="fas fa-bicycle fa-lg"></i
      ></b-form-radio>
      <b-form-radio value="car" inline>
        <i class="fas fa-car-side fa-lg"></i>
      </b-form-radio>
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
      <b-button class="mx-2" variant="danger" @click="$router.go(-1)"
        ><i class="fas fa-times"></i
      ></b-button>
      <b-button variant="success" @click="save()"
        ><i class="far fa-save" i
      /></b-button>
    </div>

<span style="font-size: 3em; color: Yellow;">
  <i class="fas fa-star"></i>
</span>


  </b-container>
</template>



<script>
import { isEqual } from "lodash";
import { mapGetters, mapActions } from "vuex";

export default {
  data() {
    return {
      vehicle: "walk",
      date: "",
      items: [
        {
          longitude: 0,
          latitude: 0,
          name: "",
          important: false,
        },
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
    ...mapActions(["updateTrip", "deleteTrip", "addTrip"]),
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
      const trip = {id:this.selectedTrip.id, vehicle: this.vehicle, date: this.date, path: this.items };
      await this.updateTrip(trip);
      alert("trip updated")
    },

    nameRequired(item) {
      if (item.important && !item.name) return false;
    },
  },
  computed: {
    ...mapGetters(["selectedTrip"]),
    lastRowIsEmpty() {
      //if (this.items === undefined) return false;
      if (this.items.length === 0) return false;
      else
        return (
          this.items.at(-1).longitude === 0 && this.items.at(-1).latitude === 0
        );
    },
  },

  created() {
    this.date = this.selectedTrip.date;
    this.vehicle = this.selectedTrip.vehicle;
    this.items = this.selectedTrip.path;
  },
};
</script>

<style scoped>
</style>
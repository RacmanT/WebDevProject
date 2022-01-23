<template>
  <b-container class="mt-2">
    <h1 class="my-4">{{ title }}</h1>

    <b-alert :show="isError" dismissible variant="danger">
      {{ errorMsg }}
    </b-alert>

    <b-form @submit.prevent="save">
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

      <b-form-input
        v-model="name"
        type="text"
        placeholder="Enter a name for the trip"
        :state="tripNameRequired(name)"
        required
      ></b-form-input>

      <b-table
        responsive
        borderless
        small
        class="mt-4"
        :items="items"
        :fields="fields"
      >
        

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
            :state="locationNameRequired(row.item)"
            :required="row.item.important && !row.item.name"
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
        <b-button
          class="mx-2"
          variant="danger"
          @click="goBack()"
          v-b-tooltip.hover
          title="Cancel and go back"
          ><i class="fas fa-times"></i
        ></b-button>
        <b-button
          variant="success"
          type="submit"
          v-b-tooltip.hover
          title="Save changes"
          ><i class="far fa-save" i
        /></b-button>
      </div>
    </b-form>
  </b-container>
</template>



<script>
import { isEqual, isEmpty, cloneDeep } from "lodash";
import { mapGetters, mapActions, mapMutations } from "vuex";

export default {
  data() {
    return {
      name: "",
      vehicle: "walk",
      date: "",
      title: "",
      isError: false,
      errorMsg: "",
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
    ...mapMutations(["setTargetTrip"]),

    goBack() {
      if (isEmpty(this.selectedTrip.id)) {
        this.$router.replace({ name: "Home" });
      } else {
        this.$router.replace({ name: "View trip" });
      }
    },

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
      if (isEmpty(this.items)) {
        this.isError = true;
        this.errorMsg = "Path cannot be empty";
        return;
      }

      const trip = {
        id: this.selectedTrip.id,
        name: this.name,
        vehicle: this.vehicle,
        date: this.date,
        path: this.items,
      };

      let requestREST;
      if (isEmpty(this.selectedTrip)) {
        requestREST = this.addTrip(trip);
      } else {
        requestREST = this.updateTrip(trip);
      }

      await requestREST
        .then(() => {
          //this.setTargetTrip(trip);
          this.$router.replace({ name: "Home" });
        })
        .catch((err) => {
          console.log(err);
          this.isError = true;
          this.errorMsg = "Ooops something went wrong!";
        });
    },

    locationNameRequired(item) {
      return item.important && isEmpty(item.name) ? false : null;
    },

    tripNameRequired(name) {
      return isEmpty(name) ? false : null;
    },
  },
  computed: {
    ...mapGetters(["selectedTrip"]),
    lastRowIsEmpty() {
      if (this.items.length === 0) return false;
      else
        return (
          this.items.at(-1).longitude === 0 && this.items.at(-1).latitude === 0
        );
    },
  },

  created() {
    if (isEmpty(this.selectedTrip)) {
      this.$router.replace({ name: "Home" });
    }

    this.title = isEmpty(this.selectedTrip.id)
      ? "Add trip"
      : `Edit ${this.selectedTrip.name}`;

    this.name = this.selectedTrip.name;
    this.date = this.selectedTrip.date;
    this.vehicle = this.selectedTrip.vehicle;
    this.items = cloneDeep(this.selectedTrip.path);
  },
};
</script>

<style scoped>
</style>
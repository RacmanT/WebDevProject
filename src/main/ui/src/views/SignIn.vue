<template>
  <b-container
    align-v="center"
    align-h="center"
    class="mt-5"
    style="max-width: 400px"
  >
    <b-card title="Sign up">
      <b-card-body>
        <b-form @submit.prevent="signIn">
          <b-form-group label="Name:" label-for="input-name">
            <b-form-input
              id="input-name"
              v-model="form.name"
              type="text"
              placeholder="Enter name"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group label="Email address:" label-for="input-email">
            <b-form-input
              id="input-email"
              v-model="form.email"
              type="email"
              placeholder="Enter email"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group label="Password:" label-for="input-password">
            <b-form-input
              id="input-password"
              type="password"
              v-model="form.password"
              placeholder="Enter password"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group>
            <b-button type="submit" variant="primary">Register</b-button>
          </b-form-group>

          <b-form-group>
            <b-link :to="{ path: 'signup' }" replace
              >Already have an account? Sign up!</b-link
            >
          </b-form-group>
        </b-form>
      </b-card-body>
    </b-card>
  </b-container>
</template>


<script>
import axios from "axios";

export default {
  data() {
    return {
      form: {
        name: "",
        email: "",
        password: "",
      },
    };
  },
  methods: {
    signIn() {
      this.$router.replace({ path: '/' })
      alert(JSON.stringify(this.form));
      axios
        .get(`${process.env.VUE_APP_REST_URL}/trip`)
        .then((response) => console.log(response.data));
    },
  },
};
</script>

<style>
</style>
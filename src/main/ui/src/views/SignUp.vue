<template>
  <b-container
    align-v="center"
    align-h="center"
    class="mt-5"
    style="max-width: 400px"
  >
    <b-card title="Sign up">
      <b-card-body>
        <b-form @submit.prevent="signUp">
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
            <b-form-checkbox v-model="form.checked" name="check-button"
              >Remember Me</b-form-checkbox
            >
          </b-form-group>

          <b-form-group>
            <b-button type="submit" variant="primary" :disabled="loading">
              <b-spinner small v-if="loading"></b-spinner>
              <span>Log in</span>
            </b-button>
          </b-form-group>

          <b-form-group>
            <b-link :to="{ path: 'signin' }" replace
              >Need an account? Sign in!</b-link
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
        email: "",
        password: "",
        checked: false,
      },
      loading: false,
    };
  },
  methods: {
    signUp() {
      this.loading = true;
      //alert(JSON.stringify(this.form));
      axios.get(`${process.env.VUE_APP_REST_URL}/trip`).then((response) => {
        console.log(response.data);
        setTimeout(() => {
          this.loading = false;
        }, 3000);
      });
      this.$router.replace({ path: "/" });
    },
  },
};
</script>

<style>
</style>
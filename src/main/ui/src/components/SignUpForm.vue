<template>
  <b-card-body class="m-3">
    <b-form @submit.prevent="signUp">
      <b-form-group label="Email address:">
        <b-form-input
          v-model="form.email"
          type="email"
          placeholder="Enter email"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group label="Password:">
        <b-form-input
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
        <b-button type="submit"  class="mt-2"  variant="primary" :disabled="loading">
          <b-spinner small v-if="loading"></b-spinner>
          <span>Log in</span>
        </b-button>
      </b-form-group>
    </b-form>
  </b-card-body>
</template>

<script>
import axios from "axios";

export default {
  name: "SignUpForm",
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
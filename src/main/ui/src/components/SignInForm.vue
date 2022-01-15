<template>


  <b-card-body class="m-3">
    <b-form @submit.prevent="signIn">
      <b-form-group label="Name:">
        <b-form-input
          v-model="form.name"
          type="text"
          placeholder="Enter name"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group label="Email address:">
        <b-form-input
          v-model="form.email"
          type="email"
          placeholder="Enter email"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group
        label="Password:"
        description="Password should be at least 6 characters long"
      >
        <b-form-input
          type="password"
          v-model="form.password"
          placeholder="Enter password"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group>
        <b-button type="submit" class="mt-3" variant="primary"
          >Register</b-button
        >
      </b-form-group>
    </b-form>
  </b-card-body>


</template>


<script>
import axios from "axios";

export default {
  name: "SignInForm",
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
      this.$router.replace({ path: "/" });
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
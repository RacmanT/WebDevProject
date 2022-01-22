<template>
  <b-card-body class="m-3">
    <h1 class="mb-4 font-weight-light">Hi, there!</h1>

    <b-form @submit.prevent="signUp">
      <b-form-group label="Email address:">
        <b-form-input
          v-model="form.email"
          type="email"
          placeholder="Enter email"
          required
          :state="validation"
        ></b-form-input>
      </b-form-group>

      <b-form-group label="Password:">
        <b-form-input
          type="password"
          v-model="form.password"
          placeholder="Enter password"
          required
          :state="validation"
        ></b-form-input>

        <b-form-invalid-feedback :state="validation">
          Wrong credentials
        </b-form-invalid-feedback>
      </b-form-group>

      <!-- <b-form-group>
        <b-form-checkbox v-model="form.checked" name="check-button"
          >Remember Me</b-form-checkbox
        >
      </b-form-group> -->

      <b-form-group>
        <b-button
          type="submit"
          class="mt-2"
          variant="primary"
          :disabled="loading"
        >
          <b-spinner small v-if="loading"></b-spinner>
          <span v-else>Log in</span>
        </b-button>
      </b-form-group>
    </b-form>
  </b-card-body>
</template>

<script>
import firebase from "firebase/compat/app";

export default {
  name: "SignUpForm",
  data() {
    return {
      form: {
        email: "",
        password: "",
        remember: false,
      },
      loading: false,
      validation: null,
    };
  },
  methods: {
    async signUp() {
      this.loading = true;
      /*  await firebase
        .auth()
        .setPersistence(
          this.form.remember
            ? firebase.auth.Auth.Persistence.LOCAL
            : firebase.auth.Auth.Persistence.SESSION
        ); */

      await firebase
        .auth()
        .signInWithEmailAndPassword(this.form.email, this.form.password)
        .then(() => this.$router.replace({ name: "Home" }))
        .catch((err) => {
          console.log(err.message);
          this.validation = false;
        })
        .finally((this.loading = false));

    },
  },
};
</script>

<style>
</style>
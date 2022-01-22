<template>
  <b-card-body class="m-3">
    <h1 class="mb-4 font-weight-light">Welcome!</h1>

    <b-form @submit.prevent="signIn">
      <b-form-group label="Name:">
        <b-form-input
          v-model="form.name"
          type="text"
          placeholder="Enter name"
          required
          :state="validation"
        ></b-form-input>
        <b-form-invalid-feedback :state="validation">
          Name must be composed only of letters.
        </b-form-invalid-feedback>
      </b-form-group>

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
          Password should be at least 6 characters long
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group>
        <b-button
          type="submit"
          class="mt-3"
          variant="primary"
          :disabled="loading"
          ><b-spinner small v-if="loading"></b-spinner>
          <span v-else>Register</span></b-button
        >
      </b-form-group>
    </b-form>
  </b-card-body>
</template>


<script>
import firebase from "firebase/compat/app";

export default {
  name: "SignInForm",
  data() {
    return {
      form: {
        name: "",
        email: "",
        password: "",
      },
      loading: false,
      validation: null,
    };
  },
  methods: {
    async signIn() {
      const mailPattern = /\S+@\S+\.\S+/;
      const namePattern = /^[A-Za-z]+$/;
      if (
        !this.form.name.match(namePattern) ||
        this.form.name.length < 3 ||
        !this.form.email.match(mailPattern) ||
        this.form.password.length < 6
      ) {
        this.validation = false;
        return;
      }

      this.loading = !this.loading;
      const user = {
        username: this.form.name,
        email: this.form.email,
        password: this.form.password,
      };

      await fetch(`${process.env.VUE_APP_REST_URL}/register`, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
      }).catch((err) => {
        console.log(err.message);
        this.validation = false;
      });

      await firebase
        .auth()
        .signInWithEmailAndPassword(this.form.email, this.form.password)
        .catch((err) => {
          console.log(err.message);
          this.validation = false;
        });

      this.loading = !this.loading;
      this.$router.replace({ name: "Home" });
    },
  },
};
</script>

<style>
</style>
<template>
  <div>
    <b-navbar variant="light">
      <b-navbar-brand :to="{ name: 'Home' }">Trip Diary</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-form>
            <b-button
              v-show="isSigned"
              right
              variant="outline-secondary"
              @click.prevent="signOut"
              >Sign Out</b-button
            >
          </b-nav-form>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import firebase from "firebase/compat/app";

export default {
  name: "Navbar",

  methods: {
    async signOut() {
      await firebase.auth().signOut();
      this.$router.replace({ name: "Authentication" });
    },
  },

  computed: {
    isSigned() {
      return this.$route.name !== "Authentication";
    },
  },
};
</script>

<style>
</style>
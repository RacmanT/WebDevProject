<template>
  <b-container
    align-v="center"
    align-h="center"
    class="mt-5"
    style="max-width: 400px"
  >
    <b-card no-body>
      <b-tabs card content-class="mt-2 " fill>
        <b-tab title="Sign up" active>
          <SignUpForm />
        </b-tab>
        <b-tab title="Sign in" lazy>
          <SignInForm />
        </b-tab>
      </b-tabs>
    </b-card>
  </b-container>
</template>

<script>
import SignInForm from "@/components/SignInForm.vue";
import SignUpForm from "@/components/SignUpForm.vue";

export default {
  components: {
    SignInForm,
    SignUpForm,
  },
};
</script>

<style>
</style>
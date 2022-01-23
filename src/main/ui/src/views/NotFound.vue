<template>
  <b-container align-v="center" align-h="center" class="mt-5">
    <b-icon icon="x-circle" variant="danger" class="m-4" scale="3" />
    <h1>Error 404</h1>
    <p>Page not Found</p>

    <b-button variant="primary" @click="$router.replace({ name: 'Home' })"
      >Home
    </b-button>
  </b-container>
</template>

<script>
</script>

<style>
</style>
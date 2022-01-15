<template>
  <div>
  <b-navbar  variant="light">
    <b-navbar-brand :to="{ path: '/' }">Trip Diary</b-navbar-brand>
    

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>

      <b-navbar-nav>
        <b-nav-item :to="{ path: 'add' }">Add trip</b-nav-item>
      </b-navbar-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">


        <b-nav-item-dropdown right>
          <template #button-content>
            User
          </template>
          <b-dropdown-item href="#">Settings</b-dropdown-item>
          <b-dropdown-item href="#">Sign Out</b-dropdown-item>
        <b-dropdown-item rel="stylesheet" href="bootswatch/dist/sketchy/bootstrap.min.css">Change</b-dropdown-item>
        </b-nav-item-dropdown>


          <!-- <b-nav-item right>
           <b-dropdown  text="User" variant="outline">
          <b-dropdown-item href="#">Settings</b-dropdown-item>
          <b-dropdown-item href="#">Sign Out</b-dropdown-item>
           </b-dropdown>
        </b-nav-item> -->


      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
</template>

<script>
export default {
    name: "Navbar",

}
</script>

<style>

</style>
 # Trip Diary

This repository contains a simple single page web application made with VueJs, Java Jersey and Firebase.

## Table of Contents

- [Workflow](#workflow)
- [Implementation](#implementation)
  - [Backend](#backend)
  - [Frontend](#frontend)


### Workflow



The web app will allow the user to authenticate or in case to create a new account. After verifying the credentials, it will redirect to the Homepage, from where the user will be able to check the details of an existing trip or add a new one.  
 If an existing trip will be chosen, then the app will redirect to a page containing a map showing the trip path and a table containing the important locations during the trip. From here the user will be able to edit or delete the trip.   
If the user would like to edit or to add an existing trip then the web app will redirect him to a new page from where he will be able to change or add a trip. Every important location must have a name and the trip must have at least one location.

A demo is available here:

[![Trip Diary Demo](https://img.youtube.com/vi/DTsIPcjWh3w/0.jpg)](https://www.youtube.com/watch?v=DTsIPcjWh3w "Trip Diary Demo")


---

### Implementation

#### Backend

The backend is implemented using [Jersey RESTfull framework](https://eclipse-ee4j.github.io/jersey/), [Firebase](https://firebase.google.com/) JWT Authentication and Firebase Firestore database. When a request arrives at the server API interface it will first check if it contains a valid JWT, using a filter. If this is the case then it will proceede with it. The request data, if any, are automatically serialized in Java objects (Trip and Location) using Jersey POJO.   
The typical requests deal with add, modifying or deleting data on the Firestore database. Firestore is a NoSQL database consisting in documents and collections. Each document (a user) have a sub-collection (trips related to this user).

Requests at */register* are an exception and don't need a JWT since they are used to to create a new user. This is a workaround, since Firebase Cloud Functions (the functions which allows the Firebase Authentication to create a new user in Firebase Firestore when registered) are not free.

After the demo the Firebase Authentication will have:
<p align="center">
    <img src="https://github.com/RacmanT/WebDevProject/blob/main/screenshots/Firebase%20Authentication.png">
</p>

While Firestore will be:

<p align="center">
    <img src="https://github.com/RacmanT/WebDevProject/blob/main/screenshots/Firestore%20DB.png">
</p>

The backend files can be found [here](https://github.com/RacmanT/WebDevProject/tree/main/src/main/java/it/units) 

#### Frontend

The frontend is made with VueJs 2 using the following dependencies:

 - [BootstrapVue](https://github.com/bootstrap-vue/bootstrap-vue) is used to integrate Bootstrap CSS and [Bootswatch theme](https://github.com/thomaspark/bootswatch)

 - [Vue Router](https://github.com/vuejs/vue-router) is used to realize the single page application and to prevent user to access pages if not authorized (Router Guard).  

- [Vuex](https://github.com/vuejs/vuex) is used to store data retrived from the backend to avoid calling multiple unnecessary requests and to make these data available to all components. Vuex will store all the user trips and will have a selected trip, to manage which info should display.

- [Leaflet](https://github.com/vue-leaflet/Vue2Leaflet) is used to draw the map. A trip is picked from the vuex store and it is converted to a GeoJSON with a function, which will map the path, and the important locations throughout the path. The GeoJSON is then passed to Leaflet which will draw it on the map.

The frontend files can be found [here](https://github.com/RacmanT/WebDevProject/tree/main/src/main/ui) 

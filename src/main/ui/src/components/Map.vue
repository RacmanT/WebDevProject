<template>
  <l-map
    style="height: 100%; width: 100%"
    :zoom="zoom"
    :options="{ zoomControl: false }"
    :center="center"
    @update:zoom="zoomUpdated"
    @update:center="centerUpdated"
    @update:bounds="boundsUpdated"
  >
    <l-control-zoom position="bottomright"></l-control-zoom>
    <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
    <l-geo-json :geojson="geojson"></l-geo-json>
  </l-map>
</template>

<script>
import { LMap, LTileLayer, LControlZoom, LGeoJson } from "vue2-leaflet";

export default {
  name: "Map",
  components: {
    LMap,
    LTileLayer,
    LControlZoom,
    LGeoJson,
  },
  props: {
    geojson: null,
  },
  data() {
    return {
      url: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      attribution:
        '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',
      zoom: 4,
      center: [47.41322, -1.219482],
      bounds: null,
    };
  },

 

  methods: {
    zoomUpdated(zoom) {
      this.zoom = zoom;
    },
    centerUpdated(center) {
      this.center = center;
    },
    boundsUpdated(bounds) {
      this.bounds = bounds;
    },
  },
};
</script>

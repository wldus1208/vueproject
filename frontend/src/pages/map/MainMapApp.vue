<template>
  <div id="mapapp">
    <MainMap />
    <SideBar class="side-bar" />
  </div>
</template>

<script>
import MainMap from '@/pages/map/MainMap'
import SideBar from '@/pages/map/SideBar'

export default {
  components: {
    MainMap,
    SideBar
  }
}
</script>

<style lang="scss">
#mapapp {
  position: absolute;
  top: 70px;
  right: 0;
  bottom: 0;
  left: 0;

  >.side-bar {
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
  }

  /* width */
  ::-webkit-scrollbar {
    width: 10px;
  }

  /* Track */
  ::-webkit-scrollbar-track {
    background: #f1f1f1;
  }

  /* Handle */
  ::-webkit-scrollbar-thumb {
    background: #888;
  }

  /* Handle on hover */
  ::-webkit-scrollbar-thumb:hover {
    background: #555;
  }
}
</style>
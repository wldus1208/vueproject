<template>
  <div class="side-bar-wrapper">
    <VueResizable class="resizable-side-bar" :width="500" :min-width="500" :max-width="Infinity" :active="['r']"
      v-if="isVisibleSideBar">
      <ReviewList v-if="isVisibleReviewList == true" />
      <ReviewForm v-else />
    </VueResizable>
    <b-Button class="side-bar-active-btn" size="sm" @click="showSideBar">
      {{ isVisibleSideBar ? '◀' : '▶' }}
    </b-Button>
  </div>
</template>

<script>
import VueResizable from 'vue-resizable';
import ReviewForm from './ReviewForm.vue';
import ReviewList from './ReviewList.vue';

export default {
  name: 'SideBar',
  components: {
    ReviewForm,
    ReviewList,
    VueResizable,
  },
  data() {
    return {
      isVisibleSideBar: true,
    }
  },
  computed: {
    
    isVisibleReviewList() {
      return this.$store.state.isVisibleReviewList;
    },
  },
  methods: {
    showSideBar() {
      this.isVisibleSideBar = !this.isVisibleSideBar;
    },
    
  }
}
</script>

<style lang="scss" scoped>
.side-bar-wrapper {
  display: flex;
  color: #fff;

  >.side-bar-active-btn {
    flex-shrink: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #000000;
    padding: 0;
    border: none;
    border-radius: unset;
    color: #fff;
    opacity: 0.5;
    width: 40px;
    height: 40px;
  }
}


</style>
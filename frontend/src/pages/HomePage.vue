<template>
    <section class="py-5 text-center container">
      <h1 class="fw-light">🎬 Movie 🎬</h1>
    </section>
    <div class="album py-5 bg-body-tertiary">
      <div class="container">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
          <div class="col" v-for="(item, idx) in state.items" :key="idx">
            <CardPage :item="item" />
          </div>
        </div>
      </div>
    </div>
    </template>
    
    <script>
    import CardPage from '@/components/CardPage.vue';
    import axios from "axios";
    import {reactive} from "vue";
    
    export default {
        name: "MoviePage" ,
        components: {
          CardPage
        },
        setup(){
          const state = reactive({
            items:[],
          })
    
          axios.get("/api/items").then((res) => {
            state.items = res.data;
          })
    
          return {state}
        }
    }
    </script>
    
    <style scoped>
    </style>
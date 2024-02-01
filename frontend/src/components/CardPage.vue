<template>
    <div class="card shadow-sm">
      <span class="img" :style="{backgroundImage: `url(${item.imgPath})`}" />
      <div class="inner-content">
        <span class="title">{{ item.name }}</span>
        <span class="director">감독: {{ item.director }}</span>
        <span class="type">장르: {{ item.type }}</span>
        <hr>
        <span class="overview">
          {{ item.content }}
        </span>
        <hr>
        <span class="people">
          {{ item.people }}
        </span>
      </div>
        <div class="card-body">
            <p class="card-text">{{ item.name }}</p>
            <div class="d-flex justify-content-between align-items-center">
              <button class="btn btn-outline-primary" @click="addTo(item.id)">예매하기</button>
              <small class="price text-muted">
                {{item.opendate}}
              </small>
              <button class="btn btn-outline-success" >리뷰쓰기</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import router from "@/router/index";
import store from "@/vuex/store";

export default {
    name: "CardPage",
    props: {
      item: []
    },
    setup(){
      
      const addTo = (itemId) =>{
        if(store.state.account.id) {
          axios.post('/api/advance/items/' + itemId).then(()=>{
            window.alert("예매완료");
          })
        }else {
          router.push({path:"/login"});
        }
        
      }
      return {addTo}
    }
}
</script>

<style scoped>
.card {
  height:680px;
  text-align:center;
  position: relative;
  overflow: hidden;
}

.card .img{
    display:inline-block;
    width:100%;
    height:100%;
    background-size:cover;
    transition: all 0.25s linear;
  }
.card:hover .img {
  opacity: 0.2;
}
.card .inner-content {
  width: 80%;
  opacity: 0;
  transform: translateX(-50%) translateY(-80%);
  position: absolute;
  top: 80%;
  left: 50%;
  transition: all 600ms ease;
  color: black;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.card:hover .inner-content {
  opacity: 1;
  top: 50%;
}
.card .inner-content .title {
  font-size: 1.5rem;
  font-weight: 800;
}
.card .inner-content .overview {
  font-size: 1.1rem;
  font-weight: 500;
  white-space: pre-line;
}

.card .inner-content .people {
  font-size: 1rem;
  font-weight: 300;
}
.card .inner-content .type {
  font-size: 0.9rem;
  font-weight: 300;
}
.card .inner-content .director {
  font-size: 1rem;
  font-weight: 300;
}
.card .inner-content hr {
  border-top: 1px solid #f1f1f1;
  width: 100%;
}
</style>
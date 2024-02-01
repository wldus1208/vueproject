<template>
  <div class="board-detail">
    <div class="common-buttons">
      <button
        type="button"
        class="w3-button w3-round w3-blue-gray"
        @click="fnUpdate()">수정</button>&nbsp;
      <button
        type="button"
        class="w3-button w3-round w3-red"
        @click="fnDelete([$route.params.id])">삭제</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-blue" @click="fnList()">목록</button>
    </div>
    <div class="board-contents">
      <h3>{{ [$route.params.id].title }}</h3>
      <div>
        <strong class="w3-large">{{ [$route.params.id].gubun }}</strong>
      </div>
    </div>
    <div class="board-contents">
      <span>{{ [$route.params.id].content }}</span>
    </div>
    <div class="common-buttons"></div>
  </div>
</template>

<script>
import {reactive} from "vue";
import axios from "axios";
import router from '@/router/index';

export default {
  setup() {
    const state = reactive({
        items: [],
        form: {
           gubun:"",
           title:"",
           content:""
        }
      });

      const fnList = ()=>{
        router.push({path:"/board/list"});
      }

      const fnDelete = (id) =>{
        if (!confirm("삭제하시겠습니까?")) return;

        axios.delete(`/api/board/${id}`).then(() => {
          window.alert("삭제되었습니다.");
        })
      };

      const fnUpdate = () =>{
        axios.patch("/api/board").then(() => {
          window.alert("수정되었습니다.");
          fnView();
        })
      };

      const fnView = (id) =>{
        axios.post('/api/board/' + id).then((res) => {
          console.log(res);
        })
        
      };

      return {state, fnList, fnDelete, fnUpdate}
    }
};
</script>
<style scoped></style>

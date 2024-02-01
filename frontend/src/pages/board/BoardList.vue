<template>
  <div class="board-list">
    <div class="common-buttons">
      <button type="button" @click="fnWrite()" class="w3-button w3-round w3-yellow" >등록</button>
    </div>
    <table class="w3-table-all">
      <thead>
        <tr>
          <th>번호</th>
          <th>구분</th>
          <th>제목</th>
          <th>등록일</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(row, idx) in state.items" :key="idx">
          <td>{{ idx + 1 }}</td>
          <td>{{ row.gubun }}</td>
          <td @click="fnView(row.id)" style="cursor: pointer;">
            <!-- <a @click="fnView(row.id)">{{ row.title }}</a> -->
            {{ row.title }}
          </td>
          <td>{{ row.created }}</td>
          <td @click="fnDelete(row.id)" style="cursor: pointer;">
            delete
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import {reactive} from "vue";
import axios from "axios";
import router from '@/router/index';

export default { 
    setup() {
      const state = reactive({
          items:[]
      });

      const load = ()=> {
        axios.get("/api/board/blist").then((res) => {
          state.items = res.data;
        })
      };
      
      load();

      const fnWrite = () =>{
       router.push({path: "./write"});
      };

      const fnDelete = (id) =>{
        if (!confirm("삭제하시겠습니까?")) return;

        axios.delete(`/api/board/${id}`).then(() => {
          window.alert("삭제되었습니다.");
          load();
        })
      };

      return {state, fnWrite, fnDelete}
    },
    methods: {
      fnView(id) {
        console.log("id", id);
        axios.post('/api/board/' + id).then((res) => {
          router.push({path:"./detail/" + id});
          console.log(res.data);
        })
      }
    }
}
</script>

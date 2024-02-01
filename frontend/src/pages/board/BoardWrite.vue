<template>
  <div class="board-detail">
    <div class="common-buttons">
      <button type="button"
        class="w3-button w3-round w3-blue-gray"
        @click="fnSave()">저장</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-blue" @click="fnList()">목록</button>
    </div>
    <div class="board-contents" >
    <select v-model="state.form.gubun">
      <option disabled value="">다음 중 하나를 선택하세요</option>
      <option value="행사/이벤트">행사/이벤트</option>
      <option value="시스템점검">시스템점검</option>
      <option value="극장">극장</option>
      <option value="제휴이벤트">제휴이벤트</option>
      <option value="기타">기타</option>
    </select>
    
    <br><br>
      <input
        type="text"
        v-model="state.form.title"
        class="w3-input w3-border"
        placeholder="제목을 입력해주세요."
      />
    </div>
    <div class="board-contents">
      <textarea
        id=""
        cols="30"
        rows="10"
        v-model="state.form.content"
        class="w3-input w3-border"
        style="resize: none"
      >
      </textarea>
    </div>
  </div>
</template>

<script>
  import {reactive} from "vue";
  import axios from "axios";
  import router from '@/router/index';

export default {
  name: "BoardWrite",
    setup(){
      const state = reactive({
        items: [],
        form: {
           gubun:"",
           title:"",
           content:""
        }
      })

      const fnSave = ()=>{
        axios.post("/api/board", state.form).then(() => {
          console.log("success");
          router.push({path:"./list"});
          window.alert("저장되었습니다.");
        })
      }

      const fnList = ()=>{
        router.push({path:"./list"});
      }

      return {state, fnSave, fnList}
    }
}
</script>
<style scoped></style>

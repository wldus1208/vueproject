<template>
    <div class="container mt-3">
        <h1 class="text-center">사용자 목록</h1>
        <table class="table tale-hover mt-3">
            <thead class="table-dark">
                <tr>
                    <th>회원번호</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>가입날짜</th>
                    <th>권한</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(m, idx) in state.items" :key="idx" class="cursor-pointer">
                    <td>{{m.id}}</td>
                    <td>{{m.name}}</td>
                    <td>{{m.email}}</td>
                    <td>{{m.regdate}}</td>
                    <td v-if="m.grade == 1">관리자</td>
                    <td v-if="m.grade == 10">사용자</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import {reactive} from "vue";
import axios from "axios";

export default { 
    setup() {
        const state = reactive({
        items:[]
      })

      axios.get("/api/account/mlist").then((res) => {
        console.log(res.data);
        state.items = res.data;
      })

      return {state}
    }
}
</script>
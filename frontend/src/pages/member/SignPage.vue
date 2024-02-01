<template>
  <div class="form-signup w-100 m-auto">
      <h1 class="h3 mb-3 fw-normal">Sign up</h1>

      <div class="form-floating">
        <input type="email" class="form-control" id="email" placeholder="name@example.com" v-model="state.form.email">
        <label for="floatingInput">Email</label>
      </div>
      <div class="form-floating">
        <input type="text" class="form-control" id="name" placeholder="Username" v-model="state.form.name">
        <label for="floatingInput">Name</label>
      </div>
      <div class="form-floating">
        <input type="password" class="form-control" id="password" placeholder="Password" v-model="state.form.password">
        <label for="floatingPassword">Password</label>
      </div>

      <button class="btn btn-primary w-100 py-2" @click="submit()">Sign Up</button>
  </div>
  </template>
  
  <script>
  import {reactive} from "vue";
  import axios from "axios";
  import router from '@/router/index';

  export default {
    name: "SignPage",
    setup(){
      const state = reactive({
        items: [],
        form: {
          name:"",
          email:"",
          password:"",
          grade: 10
        }
      })

      const submit = ()=>{
        if(state.form.email == "") {
          window.alert("이메일을 입력해주세요.");
          return false;
        } 
        if(state.form.name == "") {
          window.alert("이름을 입력해주세요.");
          return false;
        } 
        if(state.form.password == "") {
          window.alert("비밀번호를 입력해주세요.");
          return false;
        } 
        
        axios.post("/api/account/join", state.form).then(() => {
          console.log("success");
          router.push({path:"/login"});
          window.alert("회원가입처리 되었습니다.");
        })
      }

      return {state, submit}
    },
    methods: {

    }
  }
  </script>
  
  <style scoped>
  .form-signup {
    max-width: 400px;
    padding: 1rem;
  }
  
  .form-signup .form-floating:focus-within {
    z-index: 2;
  }
  
  .form-signup input[type="email"] {
    margin-bottom: 15px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
  }

  .form-signup input[type="text"] {
    margin-bottom: 15px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
  }
  
  .form-signup input[type="password"] {
    margin-bottom: 20px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
  }
  </style>
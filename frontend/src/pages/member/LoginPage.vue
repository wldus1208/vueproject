<template>
<div class="form-signin w-100 m-auto">
    <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

    <div class="form-floating">
      <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com" v-model="state.form.email">
      <label for="floatingInput">Email</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="floatingPassword" placeholder="Password" @keyup.enter="submit()" v-model="state.form.password">
      <label for="floatingPassword">Password</label>
    </div>

    <div class="form-check text-start my-3">
      <input class="form-check-input" type="checkbox" value="remember-me" id="flexCheckDefault">
      <label class="form-check-label" for="flexCheckDefault">
        Remember me
      </label>
    </div>
    <button class="btn btn-primary w-100 py-2" @click="submit()">Sign in</button>
    <p class="small text-center text-gray-soft">Don't have an account yet? <router-link to="/sign">Sign up</router-link></p>
</div>
</template>

<script>
import {reactive} from "vue";
import axios from "axios";
import store from "@/vuex/store.js";
import router from '@/router/index.js';

export default {
    setup(){
        const state = reactive({
            form :{
                email:"",
                password:""
            }
        })

        const submit = ()=>{
          if(state.form.email == "") {
          window.alert("이메일을 입력해주세요.");
          return false;
          } 
          if(state.form.password == "") {
            window.alert("비밀번호을 입력해주세요.");
            return false;
          } 
            axios.post("/api/account/login", state.form).then((res) => {
              console.log("res", res);
                store.commit('setAccount', res.data);
                if(res.data != 0) {
                    sessionStorage.setItem("grade", res.data);
                    router.push({path:"/"});
                    window.alert("로그인되었습니다.");
                }else {
                    window.alert("로그인정보가 존재하지 않습니다. \n다시 입력해주세요.");
                    
                }
                
            }).catch(() => {
                window.alert("로그인 정보가 존재하지 않습니다.");
            });
        }

        return {state, submit}
    }
}
</script>

<style scoped>
.form-signin {
  max-width: 330px;
  padding: 1rem;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>
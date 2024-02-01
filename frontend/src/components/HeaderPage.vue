<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
      <a class="navbar-brand"><router-link to="/" class="nav-link px-2 text-white">Home🏠</router-link></a>
      <div class="collapse navbar-collapse" id="navbarNavDarkDropdown">
        <ul class="navbar-nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
          <li><router-link to="/movie" class="nav-link px-2 text-white">무비차트🎬</router-link></li>
          <li><router-link to="/map" class="nav-link px-2 text-white">극장</router-link></li>
          <li><router-link to="/board/list" class="nav-link px-2 text-white">공지/뉴스📍</router-link></li>
          <!-- <li class="nav-item dropdown" v-if="$store.state.account.grade == 1"> -->
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink" role="button"
              data-bs-toggle="dropdown" aria-expanded="false">
              관리자 메뉴
            </a>
            <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
              <li><a class="dropdown-item"><router-link to="/user" class="nav-link text-white">UserList</router-link></a></li>
              <li><a class="dropdown-item"><router-link to="/admin" class="nav-link text-white">관리자 등록</router-link></a></li>
              <li><a class="dropdown-item">Something else here</a></li>
            </ul>
          </li>
        </ul>
        <div class="text-end">
          <router-link to="/login" class="btn btn-outline-light me-2"
            v-if="!$store.state.account.grade">♡Login♡</router-link>
          <a class="btn btn-outline-light me-2" v-if="$store.state.account.grade" @click="logout()">♡Logout♡</a>
          <router-link to="/sign" class="btn btn-warning" v-if="!$store.state.account.grade">♡Sign-up♡</router-link>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
import store from "@/vuex/store";
import router from "@/router/index";
export default {
  name: 'HeaderPage',
  setup() {
    const logout = () => {
      store.commit('setAccount', 0);
      sessionStorage.removeItem("id");
      router.push({ path: "/" });
      window.alert("로그아웃되었습니다.")
    }

    return { logout }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.p-3 {
  padding: 1rem !important;
}

.text-bg-dark {
  color: #fff !important;
  background-color: RGBA(var(--bs-dark-rgb), var(--bs-bg-opacity, 1)) !important;
}

*,
::after,
::before {
  box-sizing: border-box;
}

article,
aside,
div,
footer,
header,
hgroup,
main,
nav,
search,
section {
  display: block;
}

*,
::after,
::before {
  box-sizing: border-box;
}

*,
::after,
::before {
  box-sizing: border-box;
}
</style>

<template>
    <div class="review-form">
        <div class="title-area">
            <div class="goto-review-list-btn">
                <Button size="sm" variant="warning" @click="goToReviewList">
                    🤍 리뷰 목록
                </Button>
            </div>
            <input v-model="title" type="text" placeholder="❥ 극장 이름을 입력해주세요." />
        </div>
        <div class="image-area" v-if="!isDisabledInput">
            <div class="file-input-wrapper" @dragover="onDragOver" @drop="onDrop" v-if="!fileList.length">
                <input class="file-input" type="file" accept="image/*" @change="onChangeFiles" multiple />
                ❥ 사진을 업로드 해주세요
            </div>
            <div class="file-list" v-else>
                <ul v-if="fileList.length > 0">
                    <li v-for="(file, idx) in fileList" :key="idx">
                        {{ file.name }}
                        <span class="delete-file-icon" @click="deleteFile(idx)">✗</span>
                    </li>
                </ul>
                <ul>
                    <li class="file-btn-area">
                        <BButton size="sm" @click="deleteAllFile" class="file-delete-btn">
                            전체 삭제
                        </BButton>
                        <BButton size="sm" class="file-add-btn">
                            추가
                            <input class="file-input" type="file" accept="image/*" @change="onChangeFiles" multiple />
                        </BButton>
                    </li>
                </ul>
            </div>
        </div>
        <div class="location-info-area">
            <input type="text" placeholder="❥ 위치 정보 직접 입력하기" v-model="address" />
        </div>
        <div class="rate-area">
            <div class="rating">
                <input type="radio" v-model="grade" name="rating" value="5" id="5"><label
                    for="5">☆</label>&nbsp;&nbsp;&nbsp;
                <input type="radio" v-model="grade" name="rating" value="4" id="4"><label
                    for="4">☆</label>&nbsp;&nbsp;&nbsp;
                <input type="radio" v-model="grade" name="rating" value="3" id="3"><label
                    for="3">☆</label>&nbsp;&nbsp;&nbsp;
                <input type="radio" v-model="grade" name="rating" value="2" id="2"><label
                    for="2">☆</label>&nbsp;&nbsp;&nbsp;
                <input type="radio" v-model="grade" name="rating" value="1" id="1"><label
                    for="1">☆</label>&nbsp;&nbsp;&nbsp;
            </div>
        </div>
        <div class="review-area">
            <input type="text" placeholder="❥ 한줄평을 입력해주세요." v-model="content" />
        </div>
        <div class="bottom-btn-area">
            <Button class="save-btn" @click="saveReview">
                저장
            </Button>
        </div>
    </div>
    <b-Button class="side-bar-active-btn" size="sm" @click="showSideBar">
        {{ isVisibleSideBar ? '◀' : '▶' }}
    </b-Button>
</template>
<script>
import axios from 'axios';
import store from "@/vuex/store.js";

export default {
    components: {

    },

    data() {
        return {
            isVisibleSideBar: true,
            title: "",
            address: "",
            content: "",
            grade: "",
            fileList: [],
        }
    },
    created() {
        // this.$root.$refs.sideBar = this;
    },
    methods: {
        showSideBar() {
            this.isVisibleSideBar = !this.isVisibleSideBar;
        },
        async saveReview() {
            axios.post("/api/review/saveReview", {
                title: this.title,
                address: this.address,
                content: this.content,
                grade: this.grade,
                lon: this.$store.state.curLon,
                lat: this.$store.state.curLat,
                // files: this.fileList
                // }, {
                //   transformRequest: function (data) {
                //     const formData = new FormData();
                //     for (let key in data) {
                //       const value = data[key];

                //       if (!value)
                //         continue;

                //       if (key === 'files')
                //         value.forEach(file => {
                //           formData.append(key, file);
                //         })
                //       else
                //         formData.append(key, value)
                //     }
                //     return formData
                //   }
            })
            window.alert("저장되었습니다.");
            this.fileList = [];
            store.dispatch('setReviews', this);
            store.commit('setInputState', true);

            this.goToReviewList();
        },
        deleteFile(idx) {
            this.fileList.splice(idx, 1);
        },
        deleteAllFile() {
            this.fileList = [];
        },
        onChangeFiles(e) {
            this.fileList.push(...e.target.files);
        },
        onDrop(e) {
            this.fileList.push(...e.dataTransfer.files);
        },
        goToReviewList() {
            store.commit('setIsVisibleReviewList', true);
            store.commit('setIsDisabledInput', true);
        },

    }
}
</script>
<style lang="scss" scoped>
.review-form {
      background-color: rgba(0, 0, 0, 0.5);
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      padding: 10px;

      >.title-area {
        padding: 20px 10px;

        input,
        input::placeholder,
        input:focus {
          font-size: 2rem;
          font-weight: bold;
          color: #fff;
          box-shadow: none;
          background: none;
          border: none;
        }
      }

      >.image-area {
        padding: 0 10px;

        >.file-input-wrapper,
        .file-list {
          position: relative;
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 1.3rem;
          border: 5px dashed rgb(255, 255, 255, 0.5);
          border-radius: 10px;
          height: 250px;
          background-color: rgb(255, 255, 255, 0.5);
          overflow-y: auto;
          flex-direction: column;

          >ul {
            padding: 0 10px;

            >li {
              list-style: none;
              font-size: 1rem;
              word-break: break-all;

              >.delete-file-icon {
                cursor: pointer;
                padding: 10px 0 0 5px;
              }
            }

            >.file-btn-area {
              display: flex;
              padding-top: 10px;

              >.file-delete-btn {
                cursor: pointer;
                display: flex;
                align-items: center;
                font-size: 0.7rem;
                margin-right: 5px;
              }

              >.file-add-btn {
                cursor: pointer;
                display: flex;
                align-items: center;
                font-size: 0.7rem;
                position: relative;

                >.file-input {
                  cursor: pointer;
                  position: absolute;
                  right: 0;
                  top: 0;
                  bottom: 0;
                  left: 0;
                  opacity: 0;
                }
              }
            }
          }

          >.file-input {
            cursor: pointer;
            position: absolute;
            right: 0;
            top: 0;
            bottom: 0;
            left: 0;
            opacity: 0;
          }
        }
      }

      >.location-info-area {
        padding: 10px;

        input,
        input::placeholder,
        input:focus {
          font-size: 1rem;
          color: #fff;
          box-shadow: none;
          background: none;
          border: none;
        }
      }

      >.rate-area {
        padding: 0 20px;
        text-align: center;

        output {
          font-size: 2rem;
          color: #ffdd00;
          background: none;
          border: none;
          box-shadow: none;
        }
      }

      >.review-area {
        padding: 20px 10px;

        input,
        input::placeholder {
          width: 450px;
          height: 100px;
          resize: none;
          color: #fff;
          background: none;
          border: none;
          box-shadow: none;
          word-wrap: break-word;
        }

        /* width */
        ::-webkit-scrollbar {
          width: 10px;
        }

        /* Track */
        ::-webkit-scrollbar-track {
          background: #f1f1f1;
        }

        /* Handle */
        ::-webkit-scrollbar-thumb {
          background: #888;
        }

        /* Handle on hover */
        ::-webkit-scrollbar-thumb:hover {
          background: #555;
        }
      }

      >.bottom-btn-area {
        text-align: right;
        padding-right: 10px;

        >.save-btn {
          color: #fff;
          font-weight: bold;
          background-color: #ee9e06;
        }
      }


}
.rate {

border-bottom-right-radius: 12px;
border-bottom-left-radius: 12px;

}



.rating {
display: flex;
flex-direction: row-reverse;
justify-content: center
}

.rating>input {
display: none
}

.rating>label {
position: relative;
width: 1em;
font-size: 50px;
font-weight: 300;
color: #FFD600;
cursor: pointer;
}

.rating>label::before {
content: "\2605";
position: absolute;
opacity: 0
}

.rating>label:hover:before,
.rating>label:hover~label:before {
opacity: 1 !important
}

.rating>input:checked~label:before {
opacity: 1
}

.rating:hover>input:checked~label:before {
opacity: 0.4
}
</style>
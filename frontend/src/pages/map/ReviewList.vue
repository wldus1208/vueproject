<template>
    <div class="review-list-wrapper">
        <div class="header-area">
            <Button class="header-btn btn-outline-success" size="sm" @click="registerReview">
                리뷰 작성
            </Button>&nbsp;
            <Button v-if="!isEditMode" :disabled="!state.items.length" class="ml-2 btn-outline-primary" size="sm"
                @click="toggleEditMode">
                편집
            </Button>
            <Button v-if="isEditMode" class="ml-2 btn-outline-primary" size="sm" @click="toggleEditMode">
                편집 종료
            </Button>&nbsp;
            <Button v-if="isEditMode" class="ml-2 btn-outline-danger" size="sm" @click="deleteCheckedReviews">
                선택 삭제
            </Button>
            <input v-model="searchInput" class="search-input ml-2" @keydown.enter="searchReview">
            <button class="search-btn" @click="searchReview">🔍</button>
        </div> 
        <div class="review-list-area">
            <ul v-if="state.items.length > 0">
                <li v-for="(r, idx) in state.items" :key="idx">
                    <div class="review-item">
                        <div v-if="isEditMode" class="checkbox-area">
                            <input type="radio" v-model="checkedReviewIds" :value="r.id" @change="checkReview">
                        </div>
                        <div class="image-area" @click="goToReview(r.id)">
                            <img :alt="r.title" :src="`${imgDirPath}/${r.id}/${r.fileName}`" blank blank-color="grey"
                                class="review-image" />
                        </div>
                        <div class="review-info-area" @click="goToReview(r.id)">
                            <div class="review-title">
                                {{ r.title }}
                            </div>
                            <div class="review-address">
                                {{ r.address }}
                            </div>
                            <div class="review-rating">
                                <span v-if="r.grade == '5'">★ ★ ★ ★ ★</span>
                                <span v-if="r.grade == '4'">★ ★ ★ ★ ☆</span>
                                <span v-if="r.grade == '3'">★ ★ ★ ☆ ☆</span>
                                <span v-if="r.grade == '2'">★ ★ ☆ ☆ ☆</span>
                                <span v-if="r.grade == '1'">★ ☆ ☆ ☆ ☆</span>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
            <div v-else class="no-review-notice">
                등록된 리뷰가 없습니다.
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import store from '@/vuex/store.js';
import { reactive } from "vue";
import router from '@/router';

export default {
    name: 'ReviewList',
    data() {
        return {
            isEditMode: false,
            checkedReviewIds: undefined,
            searchInput: undefined,
        };
    },
    setup() {
        const state = reactive({
            items: []
        });

        axios.get("/api/review/getReviews").then((res) => {
            state.items = res.data;
        })

        return { state }
    },
    methods: {
        goToReview(id) {
            console.log(id);
        },
        registerReview() {
            store.commit('registerReview');
        },
        toggleEditMode() {
            this.checkedReviewIds = undefined;
            this.isEditMode = !this.isEditMode;
        },
        deleteCheckedReviews() {
            if (!confirm('선택한 리뷰를 삭제하시겠습니까?')) return;

            if(this.checkedReviewIds == undefined) {
                window.alert("선택한 항목이 없습니다.");
            } else {
                axios.delete(`/api/review/deleteReviews/${this.checkedReviewIds}`).then(() => {
                    window.alert("삭제되었습니다.");
                    this.toggleEditMode();
                    router.go(0);
                })
            }   
        },
        searchReview() {
            console.log(this.searchInput);
        },
    }
};
</script>
 
<style lang="scss" scoped>
.review-list-wrapper {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.5);
    padding: 10px;
    display: flex;
    flex-direction: column;

    >.header-area {
        padding: 10px;
        flex-shrink: 0;

        >.header-btn {
            // 버튼들에 클래스 추가
            flex-shrink: 0;
        }

        >.search-input {
            flex: 1;
            background-color: transparent;
            border-width: 0 0 1px 0;
            border-style: none none solid none;
            border-color: transparent transparent white transparent;
            color: white;

            &:focus {
                box-shadow: unset;
            }
        }

        >.search-btn {
            flex-shrink: 0;
            background-color: transparent;
            color: white;
            border: none;
        }
    }

    >.review-list-area {
        overflow-y: auto;
        flex: 1;

        >ul {
            list-style: none;
            padding: 10px;
            margin: 0;

            >li {
                padding: 10px;
                background-color: rgba(0, 0, 0, 0.4);
                border-radius: 5px;
                margin-bottom: 10px;

                &:hover {
                    cursor: pointer;
                    background-color: rgba(0, 0, 0, 0.5);
                }

                >.review-item {
                    display: flex;

                    >.checkbox-area {
                        margin: 10px;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                    }

                    >.image-area {
                        margin-right: 20px;

                        >.review-image {
                            width: 120px;
                            height: 100px;
                            object-fit: cover;
                        }
                    }

                    >.review-info-area {
                        flex: 1;
                        display: flex;
                        flex-direction: column;

                        >.review-title {
                            display: flex;
                            font-size: 25px;
                            flex: 1;
                        }

                        >.review-address {
                            display: flex;
                            align-items: center;
                            font-size: 12px;
                            flex: 1;
                            padding-bottom: 5px;
                        }

                        >.review-rating {
                            flex: 1;
                            width: 100px;
                            font-size: 15px;
                            background-color: transparent;
                            border: none;
                            padding: 0;
                            margin: 0;
                            color: #ffdd00;
                            height: unset;
                        }
                    }
                }
            }
        }

        >.no-review-notice {
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 25px;
        }
    }

}
</style>
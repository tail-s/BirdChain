<template>
  <v-card class="my-card" @wheel.prevent="onWheel">
    <div class="comment-list">
      <div v-for="comment in commentList" :key="comment.id">
        <div class="card-top">
          <div class="card-items">
            <v-card-title> {{ comment.nickname }}</v-card-title>
            <v-card-subtitle>{{
              transformDate(comment.createdAt)
            }}</v-card-subtitle>
          </div>
          <div class="confirm-items">
            <div
              class="icons"
              v-if="!comment.deleteFlagComment && !comment.modifyFlagComment"
            >
              <font-awesome-icon
                :icon="['fas', 'pen-to-square']"
                @click="showModifyInputComment(comment)"
              />
              <span> | </span>
              <font-awesome-icon
                :icon="['fas', 'trash']"
                @click="showInputForm(comment)"
              />
            </div>
            <div v-if="comment.modifyFlagComment" class="confirm-btns">
              <button @click="showModifyInputComment(comment)">취소</button>
              <span> | </span>
              <button type="submit" @click="doModifyComment(comment)">
                확인
              </button>
            </div>
            <div v-if="comment.deleteFlagComment" class="confirm-btns">
              <button @click="showInputForm(comment)">취소</button>
              <span> | </span>
              <button type="submit" @click="doDeleteMarker(comment)">
                확인
              </button>
            </div>
          </div>
        </div>
        <div
          v-if="comment.deleteFlagComment || comment.modifyFlagComment"
          class="password-items"
        >
          <form @submit.prevent class="password-form">
            <label class="password-label">비밀번호</label>
            <input
              type="password"
              placeholder="비밀번호를 입력해주세요."
              v-model="comment.password"
              v-focus="comment.isFocused"
              class="password-input-comment"
            />
          </form>
          <div v-if="comment.isAcceptable" class="warn-info">
            비밀번호를 잘못 입력했습니다. 다시 입력해주세요.
          </div>
        </div>
        <v-card-text v-if="!comment.modifyFlagComment">
          {{ comment.content }}</v-card-text
        >
        <form
          @submit.prevent
          v-if="comment.modifyFlagComment"
          class="modify-input-form"
        >
          <textarea
            id="content"
            v-model="comment.modiContentComment"
            class="modify-input-comment"
          ></textarea>
        </form>
      </div>
    </div>
    <div class="comment-regist">
      <CommentRegist
        :marker_id="props.marker_id"
        @reloadComment="reloadComment"
      />
    </div>
  </v-card>
</template>
<script setup>
import CommentRegist from "@/components/map/CommentRegist.vue";
import { ref, defineProps, watch, computed } from "vue";
import { getComments, modifyComment, deleteComment } from "@/api/comments";
import Swal from "sweetalert2";
import moment from "moment";

const props = defineProps({
  marker_id: {
    type: Number,
  },
});

// <댓글 조회>
const commentList = ref([]);
const getCommentList = () => {
  getComments(props.marker_id).then(({ data }) => {
    commentList.value = data.value.map((comment) => ({
      ...comment,
      deleteFlagComment: false,
      modifyFlagComment: false,
      password: null,
      isAcceptable: false,
      modiContentComment: null,
      isFocused: false,
    }));
  });
};
getCommentList();

watch(
  () => props.marker_id,
  (newValue, oldValue) => {
    if (newValue !== oldValue) {
      getCommentList();
    }
  }
);

const transformDate = (createdAt) => {
  return computed(() => moment(createdAt).format("YYYY-MM-DD HH:mm:ss")).value;
};

const reloadComment = () => {
  getCommentList();
};

// <댓글 수정>
const showModifyInputComment = (comment) => {
  comment.modifyFlagComment = !comment.modifyFlagComment;
  comment.isAcceptable = false;
  comment.password = null;
  comment.modiContentComment = comment.content;

  if (comment.modifyFlagComment) {
    // 다른 댓글들의 포커스 상태를 초기화
    commentList.value.forEach((c) => {
      c.isFocused = false;
    });
    // 클릭한 댓글에 포커스를 줌
    comment.isFocused = true;
  }
};

const doModifyComment = (comment) => {
  if (!comment.password) {
    Swal.fire({
      position: "center",
      title: "비밀번호를 입력해주세요.",
      icon: "warning",
    });
    return;
  }

  const reqForm = {
    id: comment.id,
    nickname: comment.nickname,
    content: comment.modiContentComment,
    password: comment.password,
    markerId: props.marker_id,
  };

  modifyComment(reqForm)
    .then(({ data }) => {
      if (data.status === "OK") {
        comment.modifyFlagComment = false;
        comment.isAcceptable = false;
        comment.password = null;

        comment.content = comment.modiContentComment;
        comment.modiContentComment = "";

        Swal.fire({
          position: "center",
          title: "수정되었습니다.",
          icon: "success",
        });
      }
    })
    .catch((error) => {
      Swal.fire({
        position: "center",
        title: `${error.response.data.message}`,
        icon: "error",
      }).then(function () {
        comment.isAcceptable = true;
        comment.password = null;
        setTimeout(function () {
          document.querySelector(".password-input-comment").focus();
        }, 300);
      });
    });
};

// <댓글 삭제>
const showInputForm = (comment) => {
  comment.deleteFlagComment = !comment.deleteFlagComment;
  comment.isAcceptable = false;
  comment.password = null;

  if (comment.deleteFlagComment) {
    // 다른 댓글들의 포커스 상태를 초기화
    commentList.value.forEach((c) => {
      c.isFocused = false;
    });
    // 클릭한 댓글에 포커스를 줌
    comment.isFocused = true;
  }
};

const doDeleteMarker = (comment) => {
  if (!comment.password) {
    Swal.fire({
      position: "center",
      title: "비밀번호를 입력해주세요.",
      icon: "warning",
    });
    return;
  }

  Swal.fire({
    title: "정말로 삭제하시겠습니까?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "승인",
    cancelButtonText: "취소",
    reverseButtons: true, // 버튼 순서 거꾸로
  }).then((result) => {
    if (result.isConfirmed) {
      const reqForm = {
        id: comment.id,
        nickname: comment.nickname,
        password: comment.password,
        markerId: props.marker_id,
      };
      deleteComment(reqForm)
        .then(({ data }) => {
          if (data.status === "OK") {
            comment.password = null;
            comment.isAcceptable = false;

            // 화면에서 제거
            const index = commentList.value.findIndex(
              (c) => c.id === comment.id
            );
            commentList.value.splice(index, 1);

            Swal.fire({
              position: "center",
              title: "삭제되었습니다.",
              icon: "success",
            });
          }
        })
        .catch((error) => {
          Swal.fire({
            position: "center",
            title: `"${error.response.data.message}"`,
            icon: "error",
          }).then(function () {
            comment.isAcceptable = true;
            comment.password = null;
            setTimeout(function () {
              document.querySelector(".password-input-comment").focus();
            }, 300);
          });
        });
    } else {
      setTimeout(function () {
        document.querySelector(".password-input-comment").focus();
      }, 300);
    }
  });
};

// <스크롤바 구현>
const onWheel = (event) => {
  event.preventDefault();
  const container = event.currentTarget.querySelector(".comment-list");
  container.scrollTop += event.deltaY;
};
</script>
<style scoped>
.my-card {
  width: 450px;
  height: 650px;
  position: relative;
}

.comment-list {
  height: 440px;
  overflow-y: auto;
  border-bottom: 0.1px solid rgb(190, 190, 190);
}

.comment-regist {
  position: absolute;
  left: 18px;
}

/* ---------------------------------------------------------------- */
.card-top {
  width: 420px;
  display: flex;
  justify-content: space-between;
  padding: 10px 0px 0 5px;
}

.v-card-title {
  padding-bottom: 5px;
  text-align: start;
  max-width: 270px;
}
.v-card-subtitle {
  text-align: start;
  padding-left: 17px;
}

.v-card-text {
  text-align: start;
  padding: 15px 20px 25px 21px;
}

.confirm-items {
  height: 25px;
  margin-top: 10px;
}

.icons {
  cursor: pointer;
}

.confirm-btns {
  height: 20px;
}

.password-items {
  margin-top: 5px;
  margin-left: 21px;
}

.password-form {
  display: flex;
}

.password-label {
  margin: auto 0;
}

.password-input-comment {
  margin-left: 13px;
  width: 310px;
  padding: 5px 10px;
  border: 1px solid gray;
  background: rgb(230, 230, 230);
  border-radius: 5px;
}

.warn-info {
  width: 400px;
  color: red;
  font-size: 10px;
  padding-top: 5px;
}

.modify-input-form {
  width: 429px;
}
.modify-input-comment {
  border: 1px solid gray;
  background: rgb(230, 230, 230);
  width: 390px;
  height: 100px;
  padding: 5px;
  border-radius: 5px;
  margin-top: 10px;
}
</style>

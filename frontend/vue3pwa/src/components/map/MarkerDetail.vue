<template>
  <v-card class="my-card-marker" @wheel.prevent="onWheel">
    <div class="my-card-container">
      <div class="top-container">
        <div>
          <v-card-title> {{ detailData.nickname }} </v-card-title>
          <v-card-subtitle>{{ transformDateMarker }}</v-card-subtitle>
        </div>
        <div v-if="!deleteFlagMarker && !modifyFlagMarker" class="icons">
          <font-awesome-icon
            :icon="['fas', 'pen-to-square']"
            @click="showModifyInputMarker" />
          <span> | </span>
          <font-awesome-icon :icon="['fas', 'trash']" @click="showInputForm" />
        </div>
        <div v-if="deleteFlagMarker" class="confirm-btn">
          <button type="reset" @click="showInputForm">취소</button>
          <span> | </span>
          <button type="submit" @click="doDeleteMarker">확인</button>
        </div>
        <div v-if="modifyFlagMarker">
          <button type="reset" @click="showModifyInputMarker">취소</button>
          <span> | </span>
          <button type="submit" @click="doModifyMarker">수정</button>
        </div>
      </div>
      <v-img
        v-if="!modiImageMarkerUrl"
        :src="detailData.image"
        height="300px"
        cover
        class="my-4"></v-img>
      <v-img
        v-if="modiImageMarkerUrl"
        :src="modiImageMarkerUrl"
        height="300px"
        cover
        class="my-4" />
      <v-card-text class="d-flex align-start flex-column ml-3">
        <div class="list-item">
          <p><strong>위치</strong></p>
          <p>{{ detailData.location }}</p>
        </div>
        <div class="list-item">
          <p><strong>유형</strong></p>
          <p>{{ detailData.type }}</p>
        </div>
        <p v-if="modifyFlagMarker" class="required-item" style="margin-left: 75px;">선택입력사항</p>
        <div class="list-item">
          <p><strong>내용</strong></p>
          <p v-if="!modifyFlagMarker">
            {{ detailData.content }}
          </p>
          <form
            @submit.prevent
            v-if="modifyFlagMarker"
            class="modify-form-marker">
            <textarea
              id="content"
              v-model="modiContentMarker"
              class="modify-input-marker"></textarea>
          </form>
        </div>
        <div v-if="modifyFlagMarker" class="modify-input">
          <p class="required-item">선택입력사항</p>
          <v-form>
            <v-file-input
              prepend-icon="mdi-camera"
              v-model="modiImageMarker"
              @change="previewImage"
              @click:clear="hidePreview"
              bg-color="rgb(230, 230, 230)"
              density="compact"
              variant="none"
              :rules="[img_rule]" />
          </v-form>
          <p v-if="imgValid" class="img-valid">
            올바른 이미지 파일을 선택해주세요.
          </p>
        </div>
        <div v-if="deleteFlagMarker || modifyFlagMarker" class="modify-input">
          <p class="required-item">*필수입력사항</p>
          <form @submit.prevent class="password-form">
            <label>
              <strong>비밀<br />번호</strong>
            </label>
            <input
              type="password"
              v-model="password"
              class="password-input-marker"
              autoComplete="off" />
          </form>
          <span v-if="isAcceptable" class="warn-info">
            비밀번호를 잘못 입력했습니다. 다시 입력해주세요.
          </span>
        </div>
      </v-card-text>
    </div>
  </v-card>
</template>

<script setup>
import { ref, defineProps, watch, defineEmits, computed } from "vue";
import { getMarkerDetail, deleteMarker, modifyMarker } from "@/api/markers";
import moment from "moment";
import Swal from "sweetalert2";

// 마커 상세 조회
const props = defineProps({
  marker_id: {
    type: Number,
  },
});

const emit = defineEmits(["reloadMarker", "notValid"]);

const detailData = ref({
  id: null,
  nickname: null,
  image: null,
  type: null,
  location: null,
  content: null,
  createdAt: null,
});

const transformDateMarker = computed(() =>
  moment(detailData.value.createdAt).format("YYYY-MM-DD HH:mm:ss")
);

const onWheel = (event) => {
  event.preventDefault();
  const container = event.currentTarget.querySelector(".my-card-container");
  container.scrollTop += event.deltaY;
};

const fetchMarker = () => {
  getMarkerDetail(String(props.marker_id)).then(({ data }) => {
    if (data.status === "OK") {
      detailData.value.id = data.value.id;
      detailData.value.nickname = data.value.nickname;
      detailData.value.image = data.value.image;
      detailData.value.type = data.value.type;
      detailData.value.location = data.value.location;
      detailData.value.content = data.value.content;
      detailData.value.createdAt = data.value.createdAt;
    }
  });
};

fetchMarker();
watch(
  () => props.marker_id,
  (newValue, oldValue) => {
    if (newValue !== oldValue) {
      modiImageMarkerUrl.value = null;
      modifyFlagMarker.value = false;
      deleteFlagMarker.value = false;
      password.value = null;
      isAcceptable.value = false;
      modiContentMarker.value = null;
      imgValid.value = false;
      fetchMarker();
    }
  }
);

// 유효성 체크
const imgValid = ref(false);
const img_rule = (value) => {
  if (value.length != 0) {
    const allowedExtensions = [".jpg", ".jpeg", ".png", ".gif"];
    const fileExtension = value[0].name
      .substr(value[0].name.lastIndexOf("."))
      .toLowerCase();
    if (allowedExtensions.includes(fileExtension)) {
      imgValid.value = false;
      return true;
    } else {
      imgValid.value = true;
      return false;
    }
  }
  imgValid.value = false;
  return true;
};

// <마커 수정시 이미지 미리보기>
const modiImageMarkerUrl = ref("");
const previewImage = (event) => {
  const file = event.target.files[0];
  if (!file) {
    return;
  }
  const reader = new FileReader();
  reader.readAsDataURL(file);
  reader.onload = (event) => {
    modiImageMarkerUrl.value = event.target.result;
  };
};

const hidePreview = () => {
  imgValid.value = false;
  modiImageMarkerUrl.value = null;
};

// <마커 수정>
const modiImageMarker = ref(null);
const modiContentMarker = ref("");
const modifyFlagMarker = ref(false);

const showModifyInputMarker = () => {
  modifyFlagMarker.value = !modifyFlagMarker.value;
  imgValid.value = false;
  modiContentMarker.value = detailData.value.content;
  modiImageMarkerUrl.value = detailData.value.image;
  modiImageMarker.value = null;
  password.value = null;
  isAcceptable.value = false;

  if (modifyFlagMarker.value) {
    setTimeout(function () {
      document.querySelector(".modify-input-marker").focus();
    }, 10);
  }
};

const doModifyMarker = () => {
  if (!password.value) {
    Swal.fire({
      position: "center",
      title: "비밀번호를 입력해주세요.",
      icon: "warning",
    }).then(function () {
      setTimeout(function () {
        document.querySelector(".password-input-marker").focus();
      }, 300);
    });
    return;
  }

  const reqForm = new FormData();
  const modiMarker = {
    id: detailData.value.id,
    nickname: detailData.value.nickname,
    content: modiContentMarker.value,
    password: password.value,
  };

  reqForm.append(
    "markerModifyReqDTO",
    new Blob([JSON.stringify(modiMarker)], {
      type: "application/json",
    })
  );

  if (modiImageMarker.value) {
    reqForm.append("file", modiImageMarker.value[0]);
  }
  if (!modiImageMarker.value || modiImageMarker.value.length == 0) {
    const emptyFile = new File([], { type: "image/jpeg" });
    reqForm.append("file", emptyFile);
  }

  // 이미지 파일
  modifyMarker(reqForm)
    .then(({ data }) => {
      if (data.status === "OK") {
        fetchMarker();
        modifyFlagMarker.value = false;
        modiContentMarker.value = null;
        modiImageMarkerUrl.value = null;
        modiImageMarker.value = null;
        isAcceptable.value = false;
        password.value = null;
        imgValid.value = false;

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
        title: `"${error.response.data.message}"`,
        icon: "error",
      }).then(function () {
        isAcceptable.value = true;
        password.value = null;
        setTimeout(function () {
          document.querySelector(".password-input-marker").focus();
        }, 300);
      });
    });
};

// <마커 삭제>
const deleteFlagMarker = ref(false);
const isAcceptable = ref(false);
const showInputForm = () => {
  deleteFlagMarker.value = !deleteFlagMarker.value;
  isAcceptable.value = false;
  password.value = null;

  if (deleteFlagMarker.value) {
    setTimeout(function () {
      document.querySelector(".password-input-marker").focus();
    }, 10);
  }
};

const password = ref();
const doDeleteMarker = () => {
  if (!password.value) {
    Swal.fire({
      position: "center",
      title: "비밀번호를 입력해주세요.",
      icon: "warning",
    }).then(function () {
      setTimeout(function () {
        document.querySelector(".password-input-marker").focus();
      }, 300);
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
        id: props.marker_id,
        nickname: detailData.value.nickname,
        password: password.value,
      };
      deleteMarker(reqForm)
        .then(({ data }) => {
          if (data.status === "OK") {
            password.value = null;
            isAcceptable.value = false;
            emit("reloadMarker");
            emit("notValid");

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
            title: `"${error.response.data.message.value}"`,
            icon: "error",
          }).then(function () {
            isAcceptable.value = true;
            password.value = null;
            setTimeout(function () {
              document.querySelector(".password-input-marker").focus();
            }, 300);
          });
        });
    } else {
      setTimeout(function () {
        document.querySelector(".password-input-marker").focus();
      }, 300);
    }
  });
};
</script>

<style scoped>
.my-card-marker {
  width: 450px;
  height: 650px;
  position: relative;
}
.my-card-container {
  height: 640px;
  overflow-y: auto;
}
.top-container {
  margin: 10px 0;
}

.icons {
  cursor: pointer;
}

.password-form {
  display: flex;
  margin-left: 7px;
}

.password-input-marker {
  font-size: medium;
  padding-left: 18px;
  margin-left: 16px;
  background: rgb(230, 230, 230);
  border-radius: 5px;
  width: 295px;
  height: 40px;
}

.warn-info {
  display: block;
  width: 335px;
  color: red;
  font-size: 10px;
}

.v-card-title {
  padding-bottom: 0px;
}

.v-card-subtitle {
  padding-bottom: 10px;
}

.v-card-text {
  padding-top: 0;
}

.list-item {
  display: flex;
  margin: 10px auto 0 auto;
}

.modify-input {
  margin: 0 auto;
}

.list-item p:nth-child(1) {
  margin-top: 5px;
}

.list-item p:nth-child(2) {
  border: 1px solid rgb(227, 227, 227);
  width: 295px;
  padding: 5px;
  margin-left: 15px;
  border-radius: 5px;
  text-align: start;
}

.v-card-text div:nth-child(4) {
  margin-top: 0;
}

.modify-form-marker {
  width: 305px;
  height: 50px;
}

.modify-input-marker {
  background: rgb(230, 230, 230);
  width: 295px;
  padding: 5px;
  margin-left: 15px;
  border-radius: 5px;
  text-align: start;
  margin-bottom: 0;
}

.img-valid {
  text-align: left;
  font-size: 5px;
  margin-left: 50px;
  color: red;
}
.v-form {
  display: flex;
  height: 40px;
}

.v-file-input {
  width: 335px;
  margin-left: 4px;
  height: 40px;
  max-height: 40px;
  overflow: hidden;
  white-space: nowrap;
}

.required-item {
  font-size: 5px;
  color: black;
  font-weight: bold;
  text-align: left;
  margin-top: 10px;
  margin-left: 50px;
}
</style>

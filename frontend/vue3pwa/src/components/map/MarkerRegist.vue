<template>
  <v-app>
    <v-main>
      <v-container class="pt-0">
        <div class="show-address">
          <!-- 도로명 주소 -->
          <!-- <p>{{ props.placeInfo[2] }}</p> -->
          <!-- 지명 주소 -->
          <div class="select-address">
            <div
              v-if="!props.placeInfo[3]"
              style="color: rgb(189, 189, 189); font-size: 15px">
              주소를 선택해주세요 :b
            </div>
            <div v-if="props.placeInfo[3]">{{ props.placeInfo[3] }}</div>
          </div>
          <v-btn
            variant="tonal"
            color="light-green lighten-3"
            @click="showModal"
            >게시글 등록하기</v-btn
          >
        </div>
        <v-dialog v-model="modalVisible">
          <v-card>
            <v-card-title class="text-center">
              <span class="headline">게시물 등록</span>
            </v-card-title>
            <v-card-text>
              <p class="required-input">*표시는 필수 입력 사항입니다.</p>
              <v-form @submit.prevent>
                <v-text-field
                  label="*닉네임"
                  v-model="form.nickname"
                  :rules="[nickname_rule]"></v-text-field>
                <v-text-field
                  label="*비밀번호"
                  type="password"
                  v-model="form.password"
                  :rules="[pw_rule]"></v-text-field>
                <v-file-input
                  label="*이미지"
                  prepend-icon="mdi-camera"
                  v-model="form.image"
                  @change="previewImage"
                  @click:clear="hidePreview"
                  :rules="[img_rule]"></v-file-input>
                <v-img
                  v-if="imageUrl"
                  :src="imageUrl"
                  alt="이미지 미리보기"
                  class="mb-6" />
                <v-select
                  :items="types"
                  label="*유형"
                  v-model="form.type"
                  :rules="[type_rule]"></v-select>
                <v-text-field
                  label="*위치"
                  v-model="form.location"
                  readonly
                  :rules="[location_rule]"></v-text-field>
                <v-divider></v-divider>
                <v-textarea
                  label="*글 내용"
                  v-model="form.content"
                  rows="5"
                  :rules="[content_rule]"></v-textarea>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn text @click="closeModal"> 취소 </v-btn>
              <v-btn color="success" text @click="submitForm"> 저장 </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup>
import { ref, defineProps, defineEmits } from "vue";
import { registMarker } from "@/api/markers";
import Swal from "sweetalert2";
// const { kakao } = window;

// 위도 placeInfo[0]
// 경도 placeInfo[1]
// 도로명 주소 placeInfo[2]
// 지번 주소 placeInfo[3]
const props = defineProps({
  placeInfo: {
    type: Array,
  },
  map: {
    type: Object,
  },
});

const emit = defineEmits(["reloadMarker"]);

const types = ["새발견", "버드스트라이크"];
const modalVisible = ref(false);
const form = ref({
  nickname: null,
  password: null,
  image: null,
  type: null,
  location: null,
  content: null,
});

// 유효성 검사
const nickname_rule = (value) => {
  if (!value) {
    return "닉네임은 필수 입력사항입니다.";
  }

  if (!(value && value.length <= 15)) {
    return "닉네임은 15자 이내로 입력할 수 있습니다.";
  }

  const special = value.match(
    /[\\{\\}\\[\]\\/?.,;:|\\)*~`!^\-_+<>@\\#$%&\\\\=\\(\\'\\"]/g
  );
  if (special) {
    return "특수문자는 입력할 수 없습니다.";
  }
  return true;
};

const pw_rule = (value) => {
  if (!value) {
    return "비밀번호는 필수 입력사항입니다.";
  }
  if (!(value && value.length <= 30)) {
    return "비밀번호는 30자 이내로 입력할 수 있습니다.";
  }
  return true;
};

const img_rule = (value) => {
  if (value) {
    const allowedExtensions = [".jpg", ".jpeg", ".png", ".gif", ".jfif"];
    const fileExtension = value[0].name
      .substr(value[0].name.lastIndexOf("."))
      .toLowerCase();
    if (allowedExtensions.includes(fileExtension)) {
      return true;
    } else {
      return "올바른 이미지 파일을 선택해주세요.";
    }
  } else {
    return "이미지는 필수 입력사항입니다.";
  }
};

const type_rule = (value) => {
  if (value) return true;
  return "유형은 필수 선택사항입니다.";
};

const location_rule = (value) => {
  if (value) return true;
  return "위치는 필수 선택사항입니다.";
};

const content_rule = (value) => {
  if (!value) {
    return "내용은 필수 선택사항입니다.";
  }
  if (!(value && value.length <= 255)) {
    return "내용은 공백 포함 255자 이내로 입력할 수 있습니다.";
  }
  return true;
};

// 등록창 띄우기
const showModal = () => {
  modalVisible.value = true;
  form.value.location = props.placeInfo[3];
  // if (props.placeInfo[2] == null) {
  //   form.value.location = props.placeInfo[3];
  // }
  // form.value.location = props.placeInfo[2];
};

// 사진 미리보기
const imageUrl = ref("");
const previewImage = (event) => {
  const file = event.target.files[0];

  if (!file) {
    return;
  }

  const reader = new FileReader();
  reader.readAsDataURL(file);
  reader.onload = (event) => {
    imageUrl.value = event.target.result;
  };
};

const hidePreview = () => {
  form.value.image = null;
  imageUrl.value = null;
};

// 모달창 닫기
const closeModal = () => {
  modalVisible.value = false;
  form.value.nickname = null;
  form.value.password = null;
  form.value.image = null;
  form.value.type = null;
  form.value.content = null;
  imageUrl.value = null;
};

// axios 요청
const submitForm = () => {
  if (
    !form.value.nickname ||
    !form.value.password ||
    !form.value.image ||
    !form.value.type ||
    !form.value.location ||
    !form.value.content
  ) {
    setTimeout(() => {
      modalVisible.value = false;
    }, 100);
    setTimeout(() => {
      Swal.fire({
        position: "center",
        title: "필수입력사항을\n모두 기재해주세요.",
        icon: "warning",
      }).then((result) => {
        if (result.isConfirmed) {
          modalVisible.value = true;
        }
      });
    }, 200);
    return;
  }

  const reqForm = new FormData();
  const regMarker = {
    nickname: form.value.nickname,
    type: form.value.type,
    lat: props.placeInfo[0],
    lng: props.placeInfo[1],
    location: form.value.location,
    content: form.value.content,
    password: form.value.password,
  };

  reqForm.append(
    "markerAddReqDTO",
    new Blob([JSON.stringify(regMarker)], {
      type: "application/json",
    })
  );
  reqForm.append("file", form.value.image[0]);

  registMarker(reqForm).then(({ data }) => {
    if (data.status === "OK") {
      closeModal();
      emit("reloadMarker");

      Swal.fire({
        position: "center",
        title: "등록되었습니다.",
        icon: "success",
        backdrop: false,
      });
    } else {
      closeModal();
      Swal.fire({
        position: "center",
        title: "등록에 실패했습니다.",
        icon: "error",
      });
    }
  });
};
</script>

<style scoped>
.show-address {
  padding: 0px;
  width: 31vw;
  margin: 0 auto;
}

.v-application {
  height: 129px;
}

.required-input {
  font-size: 5px;
  color: black;
  margin-bottom: 10px;
  margin-left: 5px;
  font-weight: bold;
}

.select-address {
  border: 0.5px gray solid;
  border-radius: 5px;
  padding: 5px;
  width: 27vw;
  height: 55px;
  margin: 10px auto 10px auto;
  box-shadow: 5px 2px 7px gray;
  display: flex;
  flex-direction: column;
  align-items: center; /* 가로 정렬 */
  justify-content: center; /* 세로 정렬 */
  font-size: 1vw;
}

.v-btn {
  font-size: 1.2vw;
  margin-top: 10px;
}

@media (min-width: 960px) {
  .v-container {
    max-width: 2000px;
  }
}

@media (min-width: 600px) {
  /* 노트북 크기 이상일 때 */
  .v-dialog {
    max-width: 600px;
  }
  .v-card {
    max-height: 70vh;
  }

  .flex-box {
    width: 45vw;
  }
}

@media (max-width: 300px) {
  /* 모바일 크기 일 때 */
  .v-card-title {
    justify-content: center;
  }
  .v-card-text {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}

@media (max-width: 1062px) {
  .v-btn {
    font-size: 3vw;
  }
  .select-address {
    font-size: 2vw;
  }
}
</style>

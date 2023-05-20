<template>
  <div>
    <form @submit.prevent="regist" class="regist-form">
      <div class="input-container">
        <div class="form-row">
          <p>아이디</p>
          <input
            type="text"
            v-model="form.nickname"
            placeholder="아이디를 입력해주세요." />
        </div>
        <div class="form-row">
          <p>비밀번호</p>
          <input
            type="password"
            v-model="form.password"
            placeholder="비밀번호를 입력해주세요." />
        </div>
        <div class="form-row">
          <p>내용</p>
          <textarea
            v-model="form.content"
            placeholder="내용을 입력해주세요."></textarea>
        </div>
      </div>
      <button type="submit">등록</button>
    </form>
  </div>
</template>
<script setup>
import { ref, defineProps, defineEmits } from "vue";
import { registComment } from "@/api/comments";
import Swal from "sweetalert2";

const props = defineProps({
  marker_id: {
    type: Number,
  },
});

const emit = defineEmits(["reloadComment"]);

const form = ref({
  nickname: null,
  password: null,
  content: null,
});

const regist = () => {
  const reqForm = {
    nickname: form.value.nickname,
    password: form.value.password,
    content: form.value.content,
    markerId: props.marker_id,
  };

  registComment(reqForm).then(({ data }) => {
    if (data.status === "OK") {
      form.value.nickname = null;
      form.value.password = null;
      form.value.content = null;

      Swal.fire({
        position: "center",
        title: "등록되었습니다.",
        icon: "success",
      });

      emit("reloadComment");
    } else {
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
.regist-form {
  display: flex;
}

.input-container {
  height: 185px;
  margin-top: 10px;
}

.form-row {
  display: flex;
  margin-top: 7px;
}

.form-row > p {
  width: 62px;
  text-align: start;
  margin-top: 5px;
}

.form-row > input {
  background: rgb(230, 230, 230);
  padding: 7px;
  margin-left: 5px;
  height: 40px;
  width: 280px;
}

.form-row > textarea {
  background: rgb(230, 230, 230);
  padding: 7px;
  margin-left: 5px;
  height: 80px;
  width: 280px;
}

button[type="submit"] {
  height: 175px;
  width: 50px;
  margin-top: 15px;
  margin-bottom: 10px;
  margin-left: 10px;
  background-color: #6dcd01;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button[type="submit"]:hover {
  background-color: #64ba02;
}

@media (max-width: 800px) {
  .regist-form {
    width: 350px;
  }
  .input-container {
    width: 300px;
  }

  .form-row > p {
    font-size: 13px;
  }
  .form-row > input {
    margin-left: 5px;
    height: 40px;
    width: 250px;
  }

  .form-row > textarea {
    margin-left: 5px;
    height: 80px;
    width: 330px;
  }
}
</style>

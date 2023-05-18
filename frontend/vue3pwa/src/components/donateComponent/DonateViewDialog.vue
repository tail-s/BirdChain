<template>
  <div class="text-center">
    <v-dialog v-model="dialog" width="auto">
      <template v-slot:activator="{ props }">
        <v-btn v-bind="props" @click="getAccount" class="donate-button">
          기부하기
        </v-btn>
      </template>

      <v-card v-if="account.valueOf">
        <div class="donate-card">
          <img src="@/assets/img/Donate_dialog.png" class="v-card-img" />
          <v-card-text>
            <div class="awesome-icon">
              <font-awesome-icon
                :icon="['fass', 'xmark']"
                @click="dialog = false"
                size="2xl"
                style="cursor: pointer"
              />
            </div>
            <h2 style="margin-bottom: 3vw">기부할 금액을 설정해주세요.</h2>
            <h3 style="margin-bottom: 2vw">
              기부 최소 금액은 0.001 ETH입니다.
            </h3>
            <h3>현재 잔고 :</h3>
          </v-card-text>
        </div>
        <div class="dialog-donate-btn" d><v-btn> 기부하기 </v-btn></div>
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
import { ref } from "vue";
import { createWeb3Instance } from "@/web3";

export default {
  setup() {
    const dialog = ref(false);

    const account = ref("");

    const getAccount = async () => {
      console.log("지갑 연동 실행");
      const web3 = await createWeb3Instance();
      if (web3) {
        const accounts = await web3.eth.getAccounts();
        console.log(accounts);
        account.value = accounts[0];
        dialog.value = true; // 올바르게 수정된 부분: dialog.value를 dialog로 수정
      }
    };

    return {
      dialog,
      account,
    };
  },
};
</script>
<style scoped>
.donate-button {
  margin-top: 10vw;
  font-size: 1.5vw;
}
.donate-card {
  display: flex;
  flex-direction: row;
}
.v-card-img {
  margin-top: 3vw;
  margin-left: 3vw;
  width: 20vw;
  height: 20vw;
}

.awesome-icon {
  display: flex;
  margin-bottom: 2vw;
  flex-direction: row-reverse;
}

.dialog-donate-btn {
  display: flex;
  margin-top: 3vw;
  margin-bottom: 3vw;
  justify-content: center;
}
</style>

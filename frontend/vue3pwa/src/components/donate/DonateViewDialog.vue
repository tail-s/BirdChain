<template>
  <div class="text-center">
    <v-dialog v-model="dialog" width="auto">
      <template v-slot:activator="{ props }">
        <v-btn
          color="success"
          size="x-large"
          v-bind="props"
          @click="donateBtn"
          class="donate-button"
        >
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
            <h2 style="margin-bottom: 3vw; margin-right: 3vw">
              기부할 금액을 설정해주세요.
            </h2>
            <h3 style="margin-bottom: 2vw; margin-right: 3vw">
              기부 최소 금액은 0.001 ETH입니다.
            </h3>
            <h3>현재 잔고 : {{ balance }} ETH</h3>
          </v-card-text>
        </div>

        <label for="donateAmount" style="margin-left: 3vw"
          >기부할 금액 :
        </label>
        <v-text-field
          type="number"
          id="donateAmount"
          v-model="dAmount"
          :min="0.001"
          step="0.001"
          style="margin-left: 6vw; margin-right: 6vw"
        />
        <p style="margin-left: 3vw">입력된 금액 : {{ dAmount }}</p>

        <div class="dialog-donate-btn">
          <v-btn @click="donating"> 기부하기 </v-btn>
        </div>
      </v-card>
    </v-dialog>

    <div>설정하신 금액에서 예상 가스비를 제외하고 기부가 됩니다.</div>
    <div>설정하신 금액보다 적거나 많이 ETH가 소비될 수 있습니다.</div>

    <v-overlay v-model="loading">
      <v-progress-circular
        indeterminate
        size="70"
        width="7"
        color="primary"
      ></v-progress-circular>
    </v-overlay>
  </div>
</template>
<script>
import { ref, watch } from "vue";
import { createWeb3Instance } from "@/web3";
import DonationAbi from "../../abi/Donation.json";
import router from "@/router";
import axios from "axios";
import { useAccountStore } from "@/stores/accountStore";
import { donationStore } from "@/stores/donationStore";
import { walletStore } from "@/stores/donationStore";
import { getCheckoutAccount } from "@/api/checkAccount";

export default {
  setup() {
    const dStore = donationStore();
    const wStore = walletStore();
    const loading = ref(false);
    const setDonation_id = (value) => {
      dStore.setDoation_id(value);
    };

    const setwallet = (value) => {
      wStore.setwallet(value);
    };

    const dialog = ref(false);

    const account = ref("");

    const balance = ref("");

    const dAmount = ref();

    const accountStore = useAccountStore();

    const donateBtn = async () => {
      dialog.value = false;

      const web3 = await createWeb3Instance();
      const accounts = await web3.eth.getAccounts();
      if (accounts.length == 0) {
        alert("메타모스크 로그인을 해주세요.");
        console.log("로그인 해주세요");
      } else {
        dialog.value = true;
        await getAccount();
        checkAccounts();

      }
    };

    const getAccount = async () => {
      const web3 = await createWeb3Instance();
      if (web3) {
        const accounts = await web3.eth.getAccounts();
        console.log(accounts);
        account.value = accounts[0];
        const weiBalance = await web3.eth.getBalance(account.value);
        balance.value = web3.utils.fromWei(weiBalance, "ether");
      }
    };

    watch(dialog, () => {
      dAmount.value = 0;
    });

    // 계정을 확인해서 기부 한 상태인지 아닌지에 따라서 분기처리
    const checkAccounts = async () => {
      const res = await getCheckoutAccount(account.value);

      if (res.data.value !== null) {
        router.push("/nft");
      }
    };
    const donating = async () => {
      loading.value = true;
      const web3 = await createWeb3Instance();

      const Donation = new web3.eth.Contract(
        DonationAbi,
        "0xF66a435190184e335cDD01B5eB2d11A023d6385a"
      );
      const donationAmount = web3.utils.toWei(
        dAmount.value.toString(),
        "ether"
      );

      await Donation.methods
        .donate()
        .send({
          from: account.value,
          value: donationAmount,
        })
        .then((res) => {
          axios
            .post(`https://k8b104.p.ssafy.io/api/donations`, {
              amount: dAmount.value,
              txid: res.transactionHash,
              address: account.value,
            })
            .then((res) => {
              // 해당 유저가 기부 완료 상태임을 기록, 관리해야 함.
              setDonation_id(res.data.value);
              setwallet(account.value);
              accountStore.donate();
              loading.value = false;
              router.push("/nft");
            });
        });
    };

    return {
      dialog,
      account,
      balance,
      dAmount,
      checkAccounts,
      getAccount,
      donating,
      setDonation_id,
      setwallet,
      donateBtn,
      loading,
    };
  },

  // async beforeCreate() {
  //   await this.getAccount();
  //   this.checkAccounts();
  // },
};
</script>
<style scoped>
.donate-button {
  margin-top: 3vw;
  font-size: 1.5vw;
  margin-bottom: 50px;
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

.v-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
}
</style>

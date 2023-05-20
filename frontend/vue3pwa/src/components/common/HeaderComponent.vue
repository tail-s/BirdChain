<template>
  <div class="navBox">
    <div id="navbar">
      <img
        :src="LogoUrl"
        class="Logoimg"
        @click="this.$router.push({ name: 'main' })"
        style="cursor: pointer"
      />
      <h1 class="headerLink">
        <router-link to="/birds">멸종 위기 조류</router-link>
      </h1>
      <h1 class="headerLink">
        <router-link to="/donate">기부하기</router-link>
      </h1>
      <h1 class="headerLink">
        <router-link to="/post">정보 공유</router-link>
      </h1>
      <h1 class="headerLink" v-if="accountStore.account">
        <router-link to="/mypage">마이페이지</router-link>
      </h1>
      <h1 class="headerLink">
        <router-link to="/game">게임</router-link>
      </h1>
    </div>
    <div class="text-center">
      <v-btn density="comfortable" icon="mdi-vuetify" size="3.5rem" @click="dialog = true"> <div class="questionMark" style="color: darkslategray;">?</div> </v-btn>

      <v-dialog v-model="dialog" width="auto" content-class="dialog">
        <v-card>
          <v-card-text>
            <br/>
            <h3>기부하는 방법</h3>
            <br/>
           <h4> 1. 메타마스크 설치 후 계정 생성</h4> <br/>
            <h4>2. 계정 생성 후 네트워크 Sepolia로 설정 </h4>
            * 네트워크 설정 방법 <br/>
            오른쪽 상단 프로필 클릭 > 설정 > 고급 > 테스트 네트워크 보기 > 상단에서 네트워크 변경 > 새로고침(F5) <br/>
            <br/>
            <h4>3. sepolia ether 얻기</h4>
            <a href="https://sepolia-faucet.pk910.de/">바로가기 -> https://sepolia-faucet.pk910.de/</a><br/> <br/>
            <h4>4. 기부하기를 클릭하여 기부하기!</h4>
            <h4 style="color: brown;">*기부는 pc에서만 가능합니다.</h4>
          </v-card-text>
          <v-card-actions>
            <v-btn color="teal-darken-2" block @click="dialog = false"
              >Close</v-btn
            >
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </div>
</template>

<script>
import { defineComponent, onMounted, ref } from "vue";
import { useAccountStore } from "@/stores/accountStore";

export default defineComponent({
  name: "HeaderComponent",
  setup() {
    const LogoUrl = ref(require("../../assets/img/Logo.png"));
    const accountStore = useAccountStore();
    const dialog = ref(false);

    onMounted(async () => {
      // 기존에 있는 계정 정보 가져오기
      await accountStore.getAccountAsync();

      // MetaMask 혹은 다른 Ethereum 지갑이 window.ethereum 객체를 제공하는지 확인합니다.
      if (window.ethereum) {
        // 'accountsChanged' 이벤트를 감지하고, 계정 정보를 업데이트합니다.
        window.ethereum.on("accountsChanged", async (accounts) => {
          if (accounts.length === 0) {
            accountStore.account = null;
          } else if (accounts[0] !== accountStore.account) {
            accountStore.account = accounts[0];
          }
        });
      }
    });
    return { LogoUrl, accountStore, dialog };
  },
});
</script>

<style scoped>
.navBox {
  display: flex;
  align-items: center;
  border-bottom: solid #646464;;
  background-color: white;
}

#navbar {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  display: flex;
  flex-direction: row;
  font-size: 18;
  color: #4e4e4e;
  /* line-height: 27px; */
  width: 100%;
}
a {
  text-decoration: none;
  color: #473c33;
  font-weight: 700;
}

.wallettext {
  margin-top: 5px;
  font-size: 1.5vw;
  line-height: 27px;
  color: #4e4e4e;
}

.Logoimg {
  height: 100px;
  margin-left: 2vw;
}

.walletimg {
  flex: 1;
  display: flex;
  align-self: center;
  justify-content: flex-end;
  height: 30px;
  margin-right: 20px;
}

.headerLink {
  display: flex;
  flex-direction: row;
  margin-left: 4%;
  margin-top: 40px;
  font-size: 1.3vw;
  line-height: 27px;
  font-family: GmarketSansMedium;
}

.text-center {
  margin-right: 2rem;
}

.questionMark{
  font-size: 50px;
}

@media (max-width: 1200px) {
  .wallettext {
    display: none;
  }
}

.dialog {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}

@media (max-width: 600px) {
  #navbar {
    flex-wrap: wrap;
    flex-direction: row;
    justify-content: center;
  }
  .Logoimg {
    width: 70%;
    margin-left: 7vw;
  }

  .headerLink {
    display: none;
    flex-basis: 50%;
    margin-left: 4%;
    margin-top: 10px;
  }
  .walletimg img {
    display: flex;
    justify-content: flex-end;
    width: 30px;
  }
  .wallettext {
    display: none;
  }

  .text-center {
    margin-right: 0.5rem;
  }

  .questionMark{
    font-size: 35px;
}
}
</style>

<template>
  <div id="nav">
    <HeaderComponentVue></HeaderComponentVue>
    <router-view />
  </div>
  <div id="footer">
    <FooterComponent></FooterComponent>
  </div>
  <div id="bottomnav">
    <BottomNavComponent></BottomNavComponent>
  </div>
</template>

<script>
import BottomNavComponent from "./components/common/BottomNavComponent.vue";
import FooterComponent from "./components/common/FooterComponent.vue";
import HeaderComponentVue from "./components/common/HeaderComponent.vue";
import DonationAbi from "./abi/Donation.json";
import { ref, onMounted, getCurrentInstance } from "vue";
import { useAccountStore } from "@/stores/accountStore";

export default {
  name: "App",
  components: {
    HeaderComponentVue,
    FooterComponent,
    BottomNavComponent,
  },

  setup() {
    const Web3 = require("web3");
    const eventData = ref(null);
    const instance = getCurrentInstance();
    const account = useAccountStore();

    const showNotification = (message) => {
      console.log(message);
      // Assuming $toast is available in your Vue instance
      instance.proxy.$toast.info(message);
      setTimeout(instance.proxy.$toast.clear, 3000);
    };

    const showLog = async () => {
      let web3;
      if (window.ethereum.isConnected() ) {
        console.log("MetaMask is connected!");
        web3 = new Web3(window.ethereum);
        const accounts = await web3.eth.getAccounts();
        if(accounts.length > 0)
        account.setAccount(true);   
      } else {
        web3 = new Web3(
          new Web3.providers.WebsocketProvider(
            `wss://sepolia.infura.io/ws/v3/${process.env.VUE_APP_INFURA_API_KEY}`
          )
        );
        account.setAccount(false);
      }

      console.log(web3.eth.Contract);

      const Donation = new web3.eth.Contract(
        DonationAbi,
        "0xF66a435190184e335cDD01B5eB2d11A023d6385a"
      );

      console.log(Donation.events);

      const eventName = "DonationReceived";

      // 이벤트 감시
      await Donation.events[eventName]()
        .on("data", (event) => {
          // 이벤트가 변경되면 알림을 표시
          var amount = web3.utils.fromWei(event.returnValues[1], "ether");
          showNotification(
            event.returnValues[0] +
              "계정에서 " +
              amount +
              "ETH를 기부하셨습니다."
          );
          eventData.value = event;
          console.log(event);
        })
        .on("error", (error) => {
          console.error("이벤트 감시 중 오류 발생:", error);
        });
    };

    onMounted(async () => {
      await showLog(); // showLog() 함수도 async로 변경됐으므로, 여기서 await 사용
    });

    return {
      eventData,
      showNotification,
    };
  },
};
</script>

<style>
#app {
  /* font-family: 'The Jamsil 4 Medium'; */
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#bottomnav {
  display: none;
}

@media (max-width: 600px) {
  #footer {
    display: none;
  }
  #bottomnav {
    display: block;
  }
}

h1,
h2,
h3,
h4,
h5,
h6,
v-btn {
  font-family: "GmarketSansMedium";
}
div {
  font-family: "GmarketSansMedium";
}
@media (max-width: 600px) {
  h1,
  h2,
  h3,
  h4,
  h5,
  h6,
  v-btn {
    font-family: "Cafe24Ssurround";
  }
  div {
    font-family: "GyeonggiTitleM";
  }
}
</style>

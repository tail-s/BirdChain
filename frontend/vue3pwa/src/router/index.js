import { createWebHistory, createRouter } from "vue-router";
// import App from "../App.vue";
import HomeView from "@/views/HomeView.vue";
import BirdsView from "@/views/BirdsView.vue";
import MypageView from "@/views/MypageView.vue";
import DonateView from "@/views/DonateView.vue";
import GameView from "@/views/GameView.vue";
import GameOverView from "@/views/GameOverView.vue";
import NftView from "@/views/NftView.vue";
import MapView from "@/views/MapView.vue";
// import TestView from "@/views/TestView.vue";
// import { createWeb3Instance } from "@/web3";
// import { getCheckoutAccount } from "@/api/checkAccount";
import { useAccountStore } from "@/stores/accountStore";

// const checkAccount = async () => {
//   const web3 = await createWeb3Instance();
//   if (web3) {
//     const accounts = await web3.eth.getAccounts();
//     const account = accounts[0];
//     const res = await getCheckoutAccount(account);
//     console.log(res);
//     console.log(res.data);
//     if (res.data.value === null) {
//       console.log("아직 기부 안함");
//       return false; // 아직 기부하지 않은 경우
//     } else {
//       return true; // 기부한 경우
//     }
//   }
// };
const routes = [
  {
    path: "/",
    name: "main",
    component: HomeView,
  },
  {
    path: "/birds",
    name: "birds",
    component: BirdsView,
  },
  {
    path: "/mypage",
    name: "mypage",
    component: MypageView,
  },
  {
    path: "/donate",
    name: "donateView",
    component: DonateView,
    // beforeEnter: async (to, from, next) => {
    //   const hasDonated = await checkAccount();
    //   if (hasDonated) {
    //     console.log("가나");
    //     const accountStore = useAccountStore();
    //     accountStore.donate();
    //     next("/nft"); // 기부한 경우 NFT 페이지로 이동
    //   } else {
    //     console.log("다라");
    //     next(); // 기부하지 않은 경우 원래 경로로 이동
    //   }
    // },
  },
  {
    path: "/game",
    name: "gameView",
    component: GameView,
  },
  {
    path: "/gameover",
    name: "gameover",
    component: GameOverView,
  },
  {
    path: "/nft",
    name: "nftView",
    component: NftView,
    beforeEnter: async (to, from, next) => {
      const hasDonated = useAccountStore().hasDonated;
      console.log(hasDonated);
      if (hasDonated) {
        next();
      } else {
        next("/donate");
      }
    },
  },
  {
    path: "/post",
    name: "MapView",
    component: MapView,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

<template>
  <div class="donateswiper">
    <div class="donatetitle">
      <div>코인을 기부하고 NFT를 받아가세요.</div>
    </div>
    <div style="margin-top: 3vw">
      <h3>랜덤으로 4개의 이미지를 제공하고 하나를 선택할 수 있습니다.</h3>
    </div>
    <div style="margin-bottom: 2vw">
      <h3>아래는 받아갈 수 있는 NFT의 예시입니다.</h3>
    </div>
    <swiper ref="swiperRef" v-bind="swiperOptions" class="swiper-container">
      <swiper-slide v-for="(item, i) in items" :key="i">
        <img
          :src="item.src"
          :class="{
            'carousel-item-center': i === activeIndex,
            'center-image': i === activeIndex,
          }"
        />
      </swiper-slide>
      <div class="swiper-button-next"></div>
      <div class="swiper-button-prev"></div>
    </swiper>
    <div>총 기부 금액</div>
    <div class="total">{{ totalValue }}ETH</div>
  </div>
</template>

<script>
import { Swiper, SwiperSlide } from "swiper/vue";
import SwiperCore, { Navigation } from "swiper/core";
import { ref } from "vue";
// import { createWeb3Instance } from "@/web3";
import DonationAbi from "../../abi/Donation.json";
import { onMounted } from "vue";
SwiperCore.use([Navigation]);
export default {
  components: {
    Swiper,
    SwiperSlide,
  },

  setup() {
    const items = [
      {
        src: require("@/assets/img/NFT1_carousel.png"),
      },
      {
        src: require("@/assets/img/NFT2_carousel.png"),
      },
      {
        src: require("@/assets/img/NFT3_carousel.png"),
      },
      {
        src: require("@/assets/img/NFT4_carousel.png"),
      },
      {
        src: require("@/assets/img/NFT5_carousel.png"),
      },
      {
        src: require("@/assets/img/NFT6_carousel.png"),
      },
    ];

    const swiperOptions = {
      loop: true,
      slidesPerView: 3,
      spaceBetween: 10,
      centeredSlides: true,
      navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
      },
    };

    var totalValue = ref(0);
    const Web3 = require("web3");
    const web3 = new Web3(
      new Web3.providers.WebsocketProvider(
        `wss://sepolia.infura.io/ws/v3/${process.env.VUE_APP_INFURA_API_KEY}`
      )
    );
    console.log(process.env.INFURA_API_KEY);

    const Donation = new web3.eth.Contract(
      DonationAbi,
      "0xF66a435190184e335cDD01B5eB2d11A023d6385a"
    );

    const watchTotalValue = async () => {
      await Donation.methods
        .getTotalContribution()
        .call()
        .then(function (value) {
          const total = web3.utils.fromWei(value, "ether");
          totalValue.value = total;
          console.log("값이 " + value);
        });
    };

    const eventListener = async () => {
      let web3;
        web3 = new Web3(
          new Web3.providers.WebsocketProvider(
            `wss://sepolia.infura.io/ws/v3/${process.env.VUE_APP_INFURA_API_KEY}`
          )
        );
      

      console.log(web3.eth.Contract);

      const Donation = new web3.eth.Contract(
        DonationAbi,
        "0xF66a435190184e335cDD01B5eB2d11A023d6385a"
      );

      console.log(Donation.events);

      watchTotalValue();

      const eventName = "DonationReceived";

      // 이벤트 감시
      await Donation.events[eventName]()
        .on("data", () => {
          watchTotalValue();
        })
        .on("error", (error) => {
          console.error("이벤트 감시 중 오류 발생:", error);
        });
    }

    onMounted(eventListener);

    return {
      swiperOptions,
      items,
      totalValue,
      watchTotalValue,
      eventListener,
    };
  },
};
</script>
<style>
.donateswiper {
  margin-left: 5%;
  margin-right: 5%;
  margin-bottom: 3%;
  margin-top: 5%;
}

.donatetitle {
  margin-top: 3vw;
  font: 700 3vw/1.1 Calibre-R, sans-serif;
}
.swiper-slide img {
  width: 20vw;
  height: auto;
  border-radius: 50%;
}
.swiper-slide {
  display: flex;
  justify-content: center;
  align-items: center;
}
.center-image {
  width: 400px;
  height: auto;
}
</style>

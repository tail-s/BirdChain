<template>
  <div class="backcircle">
    <div id="circle3"></div>
    <img :src="img5" id="bgbird2" />
    <Flybird></Flybird>
  </div>
  <div id="home">
    <div class="component1">
      <div
        class="mainComponent"
        data-aos="fade-right"
        data-aos-duration="1000"
        data-aos-once="true"
      >
        <img :src="img1" style="object-fit: cover" />
        <div class="textfield">
          <div>
            <h1 class="maintext">
              멸종위기에 처한<br />
              새들을 위해 기부하세요
            </h1>
            <div class="subtext">
              기부금은 새 보호를 위해 힘쓰는 환경단체에 기부됩니다.<br />
              이는 새들의 생태계와 서식지를 보호하고 환경을 개선해줍니다.<br />
              단순 기부만으로도 새들에게 안전한 서식지를 제공하고,<br />
              조류 보호 활동을 지원하여 새들의 생존을 도울 수 있습니다.<br />
            </div>
          </div>
          <div>
            <div class="subtext" style="color: darkslategray">
              지금 바로 멸종위기 새를 구하기 위해 동참해주세요!
            </div>
            <div class="totalValue" style="color: darkslategray">총 기부 금액  {{ totalValue }}ETH</div>
            <v-btn
              class="donatebtn"
              text
              height="6vw"
              size="x-large"
              @click="$router.push('/donate')"
              variant="outlined"
            >
              기부하기
            </v-btn>
          </div>
        </div>
      </div>
    </div>
    <div class="component2">
      <div
        class="mainComponent"
        data-aos="fade-left"
        data-aos-duration="1000"
        data-aos-once="true"
      >
        <div class="textfield2">
          <h1 class="maintext2">
            새들은 자연의 아름다운<br />
            선물입니다
          </h1>
          <div class="subtext2">
            새는 매우 중요한 생태계의 일원입니다.<br />
            새의 멸종은 식량 연쇄 반응과 함께 <br />
            다른 동물과 식물들도 영향을 받을 수 있습니다.<br />
            또한, 새는 농업 및 식량 생산에 중요한 역할을 합니다.<br />
            새는 자연의 다양성을 표현하는 동물입니다.<br />
            새 보호는 우리의 삶과 건강에도 긍정적인 영향을 미칩니다.
          </div>
        </div>
        <img :src="img2" style="object-fit: cover" />
      </div>
    </div>
    <div class="component3">
      <div
        class="mainComponent"
        data-aos="fade-right"
        data-aos-duration="1000"
        data-aos-once="true"
      >
        <img :src="img3" style="object-fit: cover" />

        <div class="textfield">
          <div class="maintext">우리는 그들을 <br />소중히 보호해야 합니다</div>
          <div class="subtext">
            우리는 다양한 방법으로 새를 보호할 수 있습니다.<br />
            예를 들어, 새의 서식지를 보호하고, <br />유독 물질과 오염을 줄이고,<br />
            먹이와 물을 제공하는 노력을 기울일 수 있습니다.
          </div>
          <div class="goal">
            <div class="goaldetail">
              <div class="goaldetailtitle">우리의 목표</div>
              <div class="goaldetailtext">
                멸종위기의 <br />새들을 보호하고<br />
                사람들의 관심을 <br />얻는 것입니다.
              </div>
            </div>
            <div class="goaldetail">
              <div class="goaldetailtitle">우리의 도전</div>
              <div class="goaldetailtext">
                기부자에게<br />
                NFT를 <br />발급해줌으로써 <br />
                기부를 독려합니다.
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="nftdonator">
      <h1
        class="maintext3"
        data-aos="fade-left"
        data-aos-duration="1000"
        data-aos-once="true"
      >
        기부하고 NFT를 받아가세요
      </h1>
      <swiper
        :effect="'coverflow'"
        :navigation="true"
        :grabCursor="true"
        :centeredSlides="true"
        :slidesPerView="'auto'"
        :loop="true"
        :coverflowEffect="{
          rotate: 50,
          stretch: 10,
          depth: 100,
          modifier: 1,
          slideShadows: false,
        }"
        :modules="modules"
        class="mySwiper"
      >
        <swiper-slide v-for="(product, i) in products" :key="i">
          <main-card :product="product"></main-card>
        </swiper-slide>
      </swiper>
    </div>
  </div>
</template>

<script>
import MainCard from "@/components/home/MainCard.vue";
import { EffectCoverflow, Pagination } from "swiper";
import { Swiper, SwiperSlide } from "swiper/vue";
import { Navigation } from "swiper";
import "swiper/css";
import "swiper/css/effect-coverflow";
import "swiper/css/pagination";
import "swiper/css/navigation";
import AOS from "aos";
import "aos/dist/aos.css";
import Flybird from "@/components/common/FlyBird.vue";
import { onMounted, ref } from "vue";
import DonationAbi from "../abi/Donation.json";

export default {
  name: "HomeView",
  components: {
    MainCard,
    Swiper,
    SwiperSlide,
    Flybird,
  },
  created() {
    AOS.init();
  },
  setup() {
    let totalValue = ref(0);
    const Web3 = require("web3");

    const getTotalContribution = async () => {
      const web3 = new Web3(
        new Web3.providers.WebsocketProvider(
          "wss://sepolia.infura.io/ws/v3/83705720b3404902961dbecaa2199676"
        )
      );
      // console.log(web3.eth.Contract);

      const Donation = new web3.eth.Contract(
        DonationAbi,
        "0xF66a435190184e335cDD01B5eB2d11A023d6385a"
      );

      await Donation.methods
        .getTotalContribution()
        .call()
        .then(function (value) {
          const total = web3.utils.fromWei(value, "ether");
          totalValue.value = total;
          // console.log("값이 " + value);
        });

      // 이벤트 감시
      await Donation.events
        .DonationReceived()
        .on("data", () => {
          // 이벤트가 변경되면 알림을 표시
          getTotalContribution();
        })
        .on("error", (error) => {
          console.error("이벤트 감시 중 오류 발생:", error);
        });
    };

    onMounted(getTotalContribution);

    return {
      modules: [EffectCoverflow, Pagination, Navigation],
      totalValue,
      getTotalContribution,
    };
  },
  data() {
    return {
      img1: require("../assets/img/bird3.jpg"),
      img2: require("../assets/img/bird5.jpg"),
      img3: require("../assets/img/bird4.jpg"),
      img4: require("../assets/img/bgbird1.png"),
      img5: require("../assets/img/bgbird4.png"),
      img6: require("../assets/img/bgbird5.png"),
      products: [
        {
          id: "0",
          img: require("@/assets/img/NFT1_carousel.png"),
          owner: "이인호",
          name: "Cute Bird",
        },
        {
          id: "1",
          img: require("@/assets/img/NFT2_carousel.png"),
          owner: "김소진",
          name: "Happy Bird",
        },
        {
          id: "2",
          img: require("@/assets/img/NFT3_carousel.png"),
          owner: "조은서",
          name: "Yellow Bird",
        },
        {
          id: "3",
          img: require("@/assets/img/NFT4_carousel.png"),
          owner: "현승민",
          name: "Pixel Bird",
        },
        {
          id: "4",
          img: require("@/assets/img/NFT5_carousel.png"),
          owner: "강지수",
          name: "Small Bird",
        },
        {
          id: "5",
          img: require("@/assets/img/NFT6_carousel.png"),
          owner: "남기정",
          name: "Nyam Bird",
        },
      ],
    };
  },
};
</script>

<style scoped>
#home {
  display: flex;
  flex-direction: column;
}
.component1 {
  background-color: #f7fbf3;
}

.component3 {
  background-color: #f7fbf3;
}
.mainComponent {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-bottom: 15%;
  margin-right: 15%;
  margin-left: 15%;
  margin-top: 12%;
}

.textfield {
  display: flex;
  justify-content: center;
  justify-content: space-between;
  flex-direction: column;
  margin-left: 5vw;
}

.textfield2 {
  display: flex;
  justify-content: center;
  flex-direction: column;
  margin-right: 5vw;
}

.mainComponent img {
  width: 30vw;
  box-shadow: 0px 15px 35px -5px rgba(93, 127, 110, 0.32);
  border-radius: 20px;
}

.maintext {
  font-weight: 600;
  font-size: 3vw;
  text-align: right;
  text-transform: capitalize;
  color: #02100d;
}

.maintext2 {
  font-weight: 600;
  font-size: 3vw;
  text-align: left;
  text-transform: capitalize;
  color: #02100d;
}

.maintext3 {
  font-weight: 600;
  font-size: 3vw;
  text-align: left;
  text-transform: capitalize;
  color: #02100d;
}

.subtext {
  margin-top: 1rem;
  margin-bottom: 0.5rem;
  font-weight: -50;
  word-wrap: break-word;
  color: rgb(var(--neutral700-rgb, 102, 102, 102));
  letter-spacing: -0.03em;
  overflow: hidden;
  text-overflow: ellipsis;
  font-size: 1.2vw;

  text-align: right;
  text-transform: capitalize;
  color: #444444;
}

.totalValue {
  margin-top: 1rem;
  font-weight: -50;
  word-wrap: break-word;
  letter-spacing: -0.03em;
  font-size: 1.8vw;
  text-align: right;
  /* border: 1px solid black; */
  /* padding: 0.5rem; */

  text-transform: capitalize;
  color: #444444;
}

.subtext2 {
  margin-top: 1rem;
  font-weight: -50;
  word-wrap: break-word;
  color: rgb(var(--neutral700-rgb, 102, 102, 102));
  letter-spacing: -0.03em;
  overflow: hidden;
  text-overflow: ellipsis;
  font-size: 1.2vw;

  text-align: left;
  text-transform: capitalize;
  color: #444444;
}

.goal {
  display: flex;
  flex-direction: row;
  text-align: center;
}

.goaldetail {
  width: 15vw;
  height: fit-content;
  margin: 5px;
  border: solid 1px black;
  border-radius: 5%;
  /* background-color: #f5f5f5; */
  /* box-shadow: 1px 1px 1px 1px grey; */
}

.goaldetailtitle {
  color: #204c4c;
  font-style: normal;
  font-weight: 700;
  font-size: 1.7vw;
}

.goaldetailtext {
  margin-top: 0.25rem;
  margin-bottom: 0.5rem;
  font-family: "IBM Plex Sans";
  font-style: normal;
  font-weight: 700;
  font-size: 1vw;
  text-align: center;
  text-transform: capitalize;
  color: #555555;
}

.nftdonator {
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  justify-content: space-between;
  margin-right: 15%;
  margin-left: 15%;
  margin-top: 10%;
  margin-bottom: 5%;
}

#circle1 {
  position: absolute;
  width: 55vw;
  height: 55vw;
  background-color: #eafed5;
  border-radius: 50%;
  left: 50%;
  top: -10%;
  z-index: -1;
}

#circle3 {
  position: absolute;
  width: 20vw;
  height: 20vw;
  background-color: #ff5546;
  border-radius: 50%;
  right: 10%;
  top: 100%;
  z-index: -1;
}

#bgbird {
  position: absolute;
  width: 30vw;
  right: 0px;
  /* filter: blur(2px); */
  opacity: 0.4;
}

#bgbird2 {
  position: absolute;
  width: 30vw;
  top: 108%;
  right: 50px;
}

.swiper {
  width: 100%;
  padding-top: 25px;
  padding-bottom: 50px;
}

.swiper-slide {
  background-position: center;
  background-size: cover;
  width: 350px;
  height: 500px;
}

.donatebtn {
  margin-top: 50px;
  width: 100%;
  height: 200px;
  font-size: 1.5vw;
}

/* .swiper-slide img {
  display: block;
  width: 100%;
} */

@media (max-width: 600px) {
  #home {
    display: flex;
    flex-direction: column;
  }
  .mainComponent {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-right: 5%;
    margin-left: 5%;
    margin-top: 30%;
    margin-bottom: 30%;
  }

  .textfield {
    display: flex;
    flex-direction: column;
    margin-left: 5vw;
    margin-right: 2vw;
  }

  .textfield2 {
    display: flex;
    flex-direction: column;
    margin-right: 5vw;
  }

  .mainComponent img {
    width: 25vw;
    height: auto;
    object-fit: contain;
  }

  .component2 .mainComponent img {
    width: 150px;
  }

  .maintext {
    font-size: 5vw;
    text-align: right;
    text-transform: capitalize;
    color: #02100d;
  }

  .maintext2 {
    font-size: 5vw;
    text-align: left;
    text-transform: capitalize;
    color: #02100d;
  }

  .maintext3 {
    font-size: 5vw;
    text-align: left;
    text-transform: capitalize;
    color: #02100d;
  }

  .subtext {
    /* display: none; */
    margin-top: 1rem;
    font-weight: -50;
    word-wrap: break-word;
    color: rgb(var(--neutral700-rgb, 102, 102, 102));
    letter-spacing: -0.03em;
    overflow: hidden;
    text-overflow: ellipsis;
    font-size: 1vw;

    text-align: right;
    text-transform: capitalize;
    color: #444444;
  }

  .subtext2 {
    /* display: none; */
    margin-top: 1rem;
    font-weight: -50;
    word-wrap: break-word;
    color: rgb(var(--neutral700-rgb, 102, 102, 102));
    letter-spacing: -0.03em;
    overflow: hidden;
    text-overflow: ellipsis;
    font-size: 1.4vw;

    text-align: left;
    text-transform: capitalize;
    color: #444444;
  }

  .goal {
    display: flex;
    text-align: center;
    border-radius: 12%;
  }

  .goaldetail {
    width: 27vw;
    margin: 5px;
    background-color: #f5f5f5;
    border-radius: 12%;
  }

  .goaldetailtitle {
    color: #204c4c;
    font-size: 3.5vw;
  }

  .goaldetailtext {
    margin-top: 1rem;
    text-align: center;
    text-transform: capitalize;
    color: #555555;
  }

  .nftdonator {
    display: flex;
    flex-direction: row;
    margin-left: 5%;
    margin-right: 5%;
  }

  .swiper {
    width: 100%;
    padding-bottom: 50px;
    padding-top: 0px;
  }

  .backcircle {
    display: none;
  }
}
</style>

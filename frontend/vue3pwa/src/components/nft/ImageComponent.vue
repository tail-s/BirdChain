<template lang="">
  <div style="margin-top: 30px; margin-bottom: 30px">
    <h1>기부가 완료되었습니다.</h1>
    <h1>4개의 그림 중 원하는 그림을 선택하세요!</h1>
    <div>가스비를 예측하여 예측 가스비를 환불해드립니다.</div>
  </div>
  <v-container>
    <v-row>
      <!-- 첫 번째 열 -->
      <v-col cols="12" sm="6" md="6">
        <v-card
          :elevation="elevation[0]"
          :class="hoverClass[0]"
          @mouseover="hover[0] = 0"
          @mouseleave="hover[0] = -1"
          @click="createNFT(imgA)"
        >
          <v-hover v-slot:default="{ hover }">
            <v-img :src="imgA" :class="hover ? 'blur-image' : ''"></v-img>
          </v-hover>
          <v-card-title v-if="hover[0] === 0" class="overlays"> </v-card-title>
        </v-card>
      </v-col>

      <!-- 두 번째 열 -->
      <v-col cols="12" sm="6" md="6">
        <v-card
          :elevation="elevation[1]"
          :class="hoverClass[1]"
          @mouseover="hover[1] = 1"
          @mouseleave="hover[1] = -1"
          @click="createNFT(imgB)"
        >
          <v-hover v-slot:default="{ hover }">
            <v-img :src="imgB" :class="hover ? 'blur-image' : ''"></v-img>
          </v-hover>
          <v-card-title v-if="hover[1] === 1" class="overlays"> </v-card-title>
        </v-card>
      </v-col>
    </v-row>

    <v-row>
      <!-- 세 번째 열 -->
      <v-col cols="12" sm="6" md="6">
        <v-card
          :elevation="elevation[2]"
          :class="hoverClass[2]"
          @mouseover="hover[2] = 2"
          @mouseleave="hover[2] = -1"
          @click="createNFT(imgC)"
        >
          <v-hover v-slot:default="{ hover }">
            <v-img :src="imgC" :class="hover ? 'blur-image' : ''"></v-img>
          </v-hover>
          <v-card-title v-if="hover[2] === 2" class="overlays"> </v-card-title>
        </v-card>
      </v-col>

      <!-- 네 번째 열 -->
      <v-col cols="12" sm="6" md="6">
        <v-card
          :elevation="elevation[3]"
          :class="hoverClass[3]"
          @mouseover="hover[3] = 3"
          @mouseleave="hover[3] = -1"
          @click="createNFT(imgD)"
        >
          <v-hover v-slot:default="{ hover }">
            <v-img :src="imgD" :class="hover ? 'blur-image' : ''"></v-img>
          </v-hover>
          <v-card-title v-if="hover[3] === 3" class="overlays"> </v-card-title>
        </v-card>
      </v-col>
    </v-row>
  </v-container>

  <v-overlay v-model="loading" persistent="true">
      <div class="loading">
        <v-progress-circular
          indeterminate
          size="90"
          width="9"
          color="green-darken-4"
          align-items="center"
        ></v-progress-circular>
        <br />
        <div style="background-color: rgb(000, 102, 051, 0.5); color: aliceblue">
          <div style="text-align: center">
            메타마스크가 자동으로 열리지 않았다면 메타마스크를 열어주세요.
          </div>
          <div style="text-align: center">
            블록체인에 블록을 생성하는데 시간이 걸립니다. 조금만 기다려주세요.
          </div>
        </div>
      </div>
    </v-overlay>
</template>
<script>
import axios from "axios";
import { ref, computed } from "vue";
import { createWeb3Instance } from "@/web3";
import { Web3Storage } from "web3.storage";
import NFTAbi from "../../abi/BirdNFT.json";
import router from "@/router";
import { donationStore } from "@/stores/donationStore";
import { getCheckoutAccount } from "@/api/checkAccount";
import { selectNFT } from "@/api/checkAccount";
import { donateAccount } from "@/api/checkAccount";

export default {
  setup() {
    const Dstore = donationStore();
    const donation_id = Dstore.donation_id;

    const loading = ref(false);

    const itemName = [
      "Aquila chrysaetos",
      "Eurynorhynchus pygmeus",
      "Egretta eulophotes",
      "Grus japonensis",
      "Falco peregrinus",
      "Ciconia nigra",
      "Platalea minor",
      "Haliaeetus pelagicus",
      "Tringa guttifer",
      "Dryocopus javensis",
      "Mergus squamatus",
      "Cygnus olor",
      "Ciconia boyciana",
      "Haliaeetus albicilla",
      "Anser cygnoides",
      "Larus saundersi",
      "Haematopus ostralegus",
      "Emberiza aureola",
      "Grus grus",
      "Cygnus columbianus",
      "Larus relictus",
      "Terpsiphone atrocaudata",
      "Strix uralensis",
      "Dryocopus martius",
      "Platalea leucorodia",
      "Otis tarda",
      "Aegypius monachus",
      "Nipponia nippon -> Crested ibis",
      "Gallicrex cinerea -> Watercock",
      "Emberiza sulphurata -> Yellow bunting",
      "Pandion haliaetus -> Osprey",
      "Pernis ptilorhynchus -> Crested honey buzzard",
      "Accipiter soloensis",
      "Calidris tenuirostris",
      "Gorsachius goisagi",
      "Synthliboramphus wumizusume -> Japanese murrelet",
      "Galerida cristata",
      "Accipiter nisus",
      "Falco subbuteo",
      "Locustella pleskei",
      "Milvus migrans",
      "Emberiza yessoensis",
      "Bubo bubo",
      "Circus melanoleucos",
      "Numenius madagascariensis",
      "Columba rupestris",
      "Strix aluco",
      "Grus vipio",
      "Circus cyaneus",
      "Accipiter gularis",
      "Accipiter gentilis",
      "Cygnus cygnus",
      "Anser fabalis",
      "Ixobrychus eurhythmus",
      "Buteo hemilasius",
      "Pitta nympha",
      "Aquila clanga",
      "Branta bernicla",
      "Grus monacha",
      "Columba janthina",
      "Charadrius placidus",
      "Anser erythropus",
      "Aquila heliaca",
    ];

    const level = [
      "EN",
      "CR",
      "EN",
      "EN",
      "VU",
      "EN",
      "VU",
      "EN",
      "EN",
      "RE",
      "EN",
      "EN",
      "EN",
      "VU",
      "EN",
      "EN",
      "VU",
      "VU",
      "LC",
      "VU",
      "EN",
      "VU",
      "EN",
      "VU",
      "VU",
      "EN",
      "VU",
      "RE",
      "VU",
      "VU",
      "VU",
      "VU",
      "VU",
      "NE",
      "EN",
      "EN",
      "VU",
      "VU",
      "VU",
      "VU",
      "VU",
      "VU",
      "VU",
      "LC",
      "VU",
      "LC",
      "VU",
      "EN",
      "LC",
      "VU",
      "VU",
      "VU",
      "LC",
      "VU",
      "LC",
      "RE",
      "VU",
      "VU",
      "VU",
      "VU",
      "VU",
      "VU",
      "VU",
    ];

    const hover = ref([-1, -1, -1, -1]);

    const NFTNum = ref("");

    const imgA = ref("");

    const imgB = ref("");

    const imgC = ref("");

    const imgD = ref("");

    const account = ref("");

    const accountDonation = ref("");

    const client = new Web3Storage({
      token:
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkaWQ6ZXRocjoweDlkNzc2YkFlMEQ5NjA5MzZEOTQ1Nzc0MTI4MDdiN0EwM2NGYTFFRjMiLCJpc3MiOiJ3ZWIzLXN0b3JhZ2UiLCJpYXQiOjE2ODM3NjM0OTk1MjcsIm5hbWUiOiJiaXJkY2hhaW4ifQ.9ZXu8rmPVlISEhkEFW-sfrnjV9gN1wjCeiu3bDTLQ9c",
    });
    const check = ref("");

    const elevation = computed(() =>
      hover.value.map((val, index) => (val === index ? 8 : 2))
    );
    const hoverClass = computed(() =>
      hover.value.map((val, index) => (val === index ? "elevation-hover" : ""))
    );

    // 현재 계정이 기부한 계정인지 아닌지
    const checkAccount = async () => {
      const web3 = await createWeb3Instance();

      if (web3) {
        const accounts = await web3.eth.getAccounts();
        account.value = accounts[0];
      }
      console.log(account.value);
      console.log("계정 확인");
      const res = await getCheckoutAccount(account.value);

      console.log(res.data.value);
      if (res.data.value === null) {
        check.value = false;
      } else {
        check.value = true;
      }
    };

    const createNFT = async (imgURI) => {
      loading.value = true;

      let CID;
      // console.log(itemName[parseInt((NFTNum.value - 1) / 5)]);
      // console.log(imgURI);
      // console.log(level[parseInt((NFTNum.value - 1) / 5)]);

      // IPFS에 metadata 저장
      function createMetadata() {
        const metadata = JSON.stringify({
          name: itemName[parseInt((NFTNum.value - 1) / 5)],
          image: imgURI,
          iucn: level[parseInt((NFTNum.value - 1) / 5)],
        });

        const encoder = new TextEncoder();
        const metadataArray = encoder.encode(metadata);

        const metadataBlob = new Blob([metadataArray], {
          type: "application/json",
        });
        const metadataFile = new File([metadataBlob], "metadata.json");
        return metadataFile;
      }

      try {
        showOverlay.value = true;
        const metadataFile = createMetadata();
        CID = await client.put([metadataFile]);
        console.log("메타데이터 저장 완료 : ", CID);
      } catch (error) {
        console.log("메타데이터 저장 실패 : ", error);
      }

      const web3 = await createWeb3Instance();

      if (web3) {
        const accounts = await web3.eth.getAccounts();
        account.value = accounts[0];
      }
      const NFT = new web3.eth.Contract(
        NFTAbi,
        "0xc1159864630B7e366F3c77A8Bda7a959d187BD36"
      );

      console.log(CID);
      await NFT.methods
        .createNFT(imgURI, CID)
        .send({
          from: account.value,
        })
        .then((res) => {
          console.log(res);
          console.log("NFT 발급 완료");

          console.log(imgURI);
          console.log(CID);
          console.log(level[parseInt((NFTNum.value - 1) / 5)]);
          console.log(itemName[parseInt((NFTNum.value - 1) / 5)]);
          console.log(donation_id);
          console.log(accountDonation.value);
          axios
            .post(`https://k8b104.p.ssafy.io/api/items`, {
              imageUrl: imgURI,
              metadataUrl: CID,
              iucn: level[parseInt((NFTNum.value - 1) / 5)],
              name: itemName[parseInt((NFTNum.value - 1) / 5)],
              donationId: accountDonation.value,
            })
            .then(() => {
              console.log("item 등록 완료");
              selectNFT(account.value);
              loading.value = false;

              router.push("/mypage");
            });
        });
    };

    const showOverlay = ref(false); // 오버레이 표시 여부를 관리하는 상태

    return {
      hover,
      elevation,
      hoverClass,
      NFTNum,
      imgA,
      imgB,
      imgC,
      imgD,
      account,
      itemName,
      level,
      client,
      createNFT,
      check,
      checkAccount,
      accountDonation,
      showOverlay,
      loading,
    };
  },

  async mounted() {
    await this.checkAccount();
    console.log(this.account);

    if (this.check === false) {
      const img_num = donationStore().donation_id;
      this.accountDonation = img_num;
      axios
        .get(`https://k8b104.p.ssafy.io/api/nft/available`)
        .then((res) => {
          this.NFTNum = res.data.value;
          console.log("NFTNum : ", this.NFTNum);
          const imageNames = ["A", "B", "C", "D"];
          const imageRequests = imageNames.map((name) =>
            axios.get(
              `https://k8b104.p.ssafy.io/api/nft/images?fileName=${this.NFTNum.toString().padStart(
                3,
                "0"
              )}${name}`
            )
          );

          axios
            .all(imageRequests)
            .then(
              axios.spread((...responses) => {
                const images = [
                  responses[0].data.value,
                  responses[1].data.value,
                  responses[2].data.value,
                  responses[3].data.value,
                ];
                this.imgA = images[0];
                this.imgB = images[1];
                this.imgC = images[2];
                this.imgD = images[3];
              })
            )
            .catch((error) => {
              console.log(error);
            });

          const reqForm = {
            wallet: this.account,
            num: this.NFTNum.toString().padStart(3, "0"),
            imgNum: img_num,
          };
          console.log(reqForm);

          donateAccount(reqForm);
        })
        .catch((error) => {
          console.log(error);
        });
    } else {
      console.log("기부 이미 했을 때");

      getCheckoutAccount(this.account).then(({ data }) => {
        const NFTnums = data.value.num;
        const imageNames = ["A", "B", "C", "D"];
        const accountDonationId = data.value.imgNum;
        console.log(accountDonationId);
        this.accountDonation = accountDonationId;
        const imageRequests = imageNames.map((name) =>
          axios.get(
            `https://k8b104.p.ssafy.io/api/nft/images?fileName=${NFTnums}${name}`
          )
        );
        axios
          .all(imageRequests)
          .then(
            axios.spread((...responses) => {
              const images = [
                responses[0].data.value,
                responses[1].data.value,
                responses[2].data.value,
                responses[3].data.value,
              ];
              this.imgA = images[0];
              this.imgB = images[1];
              this.imgC = images[2];
              this.imgD = images[3];
            })
          )
          .catch((error) => {
            console.log(error);
          });
      });
    }
  },
};
</script>
<style scoped>
.img-div {
  display: flex;
  flex-direction: column;
}
img {
  width: 300px;
  height: 300px;
  margin-right: 10px;
  margin-bottom: 10px;
}

.blur-image {
  filter: blur(3px);
}

.overlays {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  font-weight: bold;
}

.elevation-hover {
  transition: transform 0.3s;
  transform: translateY(-5px);
}
.loading {
  display: flex;
  flex-direction: column;
  align-items: center;
}

/* .align-center {
  position: fixed;
  top: 50vh;
} */

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

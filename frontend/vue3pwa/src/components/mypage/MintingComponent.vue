<template lang="">
  <div class="mintingbox">
    <div id="mynfttext">나의 NFT 정보</div>
    <v-table fixed-header height="300px">
      <thead>
        <tr>
          <th class="text-left">NFT</th>
          <th class="text-left">Value</th>
          <th class="text-left">TXID</th>
          <th class="text-left">IUCN</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="item in items"
          :key="item.name"
        >
          <td class="text-left" id="nftid">
            <img :src=item.imageUrl id="nftimg" /> 
            <div id="nftname">{{ item.name }}</div></td>
          <td class="text-left">{{item.amount}}</td>
          <td class="text-left" id="txid">{{ item.txid }}</td>
          <td class="text-left">
            <div v-if="item.iucn === 'VU'">취약</div>
            <div v-if="item.iucn === 'LC'">관심대상</div>
            <div v-if="item.iucn === 'NT'">준위협</div>
            <div v-if="item.iucn === 'EN'">위기</div>
            <div v-if="item.iucn === 'CR'">위급</div>
            <div v-if="item.iucn === 'RE'">지역절멸</div>
          </td>
        </tr>
      </tbody>
    </v-table>
  </div>
</template>
<script>
import axios from "axios";
import { walletStore } from "@/stores/donationStore";
import { createWeb3Instance } from "@/web3.js";

export default {
  setup() {},

  name: "MintingComponent",
  data() {
    return {
      items: [],
      img1: require("../../assets/img/image 14.png"),
    };
  },
  async mounted() {
    await createWeb3Instance();
    const wStore = walletStore();
    const address = wStore.wallet;

    axios
      .get(`https://k8b104.p.ssafy.io/api/items?address=${address}`)
      .then((res) => {
        this.items = res.data.value;
      });
  },
};
</script>
<style>
.mintingbox {
  padding-right: 5vw;
  padding-left: 3vw;
  padding-top: 2vw;
  padding-bottom: 5vw;
  margin: 0 5vw 0vw 5vw;
  background: white;
}
#txid {
  width: 20%;
}

#nftid {
  display: flex;
  flex-direction: row;
  margin-top: 10px;
}
#nftimg {
  margin-right: 10px;
  width: 50px;
  height: 50px;
}
#nftname{
  align-items: center;
  display: flex;
}
</style>

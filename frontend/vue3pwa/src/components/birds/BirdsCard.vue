<template>
  <div class="flip-card" :class="{ 'show-overlay': showOverlay }">
    <div class="flip-card-inner" v-on:click="flipCard">
      <div class="flip-card-front">
        <v-card class="" width="22vw" min-width="300px" style="cursor: pointer">
          <v-img :src="birdData.img" height="300px" cover></v-img>
          <v-card-title> {{ birdData.국명 }} </v-card-title>
          <v-card-subtitle style="font-style: italic">
            학명 : {{ birdData.학명 }}
          </v-card-subtitle>
          <v-card-subtitle
            v-if="birdData.국가적색목록 === 'RE'"
            style="color: purple"
            >지역절멸</v-card-subtitle
          >
          <v-card-subtitle
            v-if="birdData.국가적색목록 === 'CR'"
            style="color: red"
            >위급</v-card-subtitle
          >
          <v-card-subtitle
            v-if="birdData.국가적색목록 === 'EN'"
            style="color: brown"
            >위기</v-card-subtitle
          >
          <v-card-subtitle
            v-if="birdData.국가적색목록 === 'VU'"
            style="color: orange"
            >취약</v-card-subtitle
          >
          <v-card-subtitle
            v-if="birdData.국가적색목록 === 'NT'"
            style="color: yellow"
            >준위협</v-card-subtitle
          >
          <v-card-subtitle
            v-if="birdData.국가적색목록 === 'LC'"
            style="color: green"
            >관심대상</v-card-subtitle
          >
        </v-card>
      </div>
      <div class="overlay">
        <p class="text">{{ birdData.text }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/json/bird.json";
import { ref } from "vue";
export default {
  name: "BirdsCard",
  props: {
    birdData: {
      type: Object,
      required: true,
    },
  },
  setup() {
    const showOverlay = ref(false);

    const flipCard = async () => {
      showOverlay.value = !showOverlay.value;
    };
    return {
      showOverlay,
      flipCard,
    };
  },
};
</script>

<style lang="scss">
$flip-card: rgba(0, 0, 0, 0.2);

*,
*:before,
*:after {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

.flip-card:hover {
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}
.flip-card,
.overlay {
  text-align: center;
  cursor: pointer;
}

img,
.overlay {
  transition: 0.3s all;
  border-radius: 3px;
}
.text {
  margin-top: 2vw;
  margin-left: 1vw;
  margin-right: 1vw;
}

.flip-card {
  margin-top: 100px;
  margin-right: 20px;
  margin-left: 20px;
  float: left;
  min-width: 300px;
  max-width: 31%;
  position: relative;
  img {
    width: 100%;
    margin-bottom: -4px;
  }
  .overlay {
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    width: 100%;
    background: $flip-card;
    color: #fff;
    opacity: 0;
    h2 {
      padding-top: 20%;
      font-family: "Droid Serif", serif;
    }
    p {
      font-family: "Julius Sans One", sans-serif;
    }
  }
  &.show-overlay .overlay {
    opacity: 1;
  }
  &.show-overlay img {
    -webkit-filter: blur(2px);
    filter: blur(2px);
  }
}
</style>

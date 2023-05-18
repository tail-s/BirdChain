<template>
  <div class="birdanimation">
    <Flybird></Flybird>
  </div>
  <div id="birds">
    <div class="d-flex align-center flex-column pa-6" style="width: 100vw">
      <v-btn-toggle
        v-model="selectedToggles"
        color="green"
        variant="outlined"
        multiple=""
      >
        <v-btn value="RE" color="purple">지역절멸</v-btn>
        <v-btn value="CR" color="red">위급</v-btn>
        <v-btn value="EN" color="brown">위기</v-btn>
        <v-btn value="VU" color="orange">취약</v-btn>
        <v-btn value="NT" color="yellow">준위협</v-btn>
        <v-btn value="LC" color="green">관심대상</v-btn>
      </v-btn-toggle>
    </div>
    <div>
      <v-card class="mx-auto" color="grey-lighten-5" max-width="400">
        <v-card-text>
          <v-text-field
            :loading="loading"
            density="compact"
            variant="solo"
            label="새 이름을 입력하세요"
            single-line
            hide-details
            v-model="name"
          ></v-text-field>
        </v-card-text>
      </v-card>
    </div>

    <div class="cardsection">
      <BirdsCard
        v-for="(bird, index) in filteredBirds"
        :key="index"
        :birdData="{ ...bird, img: require(`@/assets/img/${bird.img}`) }"
      ></BirdsCard>
    </div>
    <InfiniteLoading @infinite="load" />
  </div>
</template>

<script>
import { ref, computed } from "vue";
import BirdsCard from "@/components/birds/BirdsCard.vue";
import birds from "../assets/json/bird.json";
import Flybird from "@/components/common/FlyBird.vue";

export default {
  name: "BirdsView",
  components: {
    BirdsCard,
    Flybird
  },
  setup() {
    const selectedToggles = ref([]);
    const loading = ref(false);
    const name = ref("");

    const filteredBirds = computed(() => {
      let filtered = birds;

      if (selectedToggles.value.length > 0) {
        filtered = filtered.filter((bird) =>
          selectedToggles.value.includes(bird.국가적색목록)
        );
      }

      if (name.value) {
        filtered = filtered.filter((bird) =>
          bird.국명.toLowerCase().includes(name.value.toLowerCase())
        );
      }

      return filtered;
    });

    return {
      selectedToggles,
      loading,
      name,
      filteredBirds,
    };
  },
};
</script>

<style>

.birdanimation{
  z-index: 10;
}
.cardsection {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  flex-wrap: wrap;
  margin-right: 10vw;
  margin-left: 10vw;
  z-index: 1;
}
</style>

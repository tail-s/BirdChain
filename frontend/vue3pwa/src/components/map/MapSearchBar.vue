<template>
  <div>
    <v-toolbar dense floating>
      <v-text-field
        hide-details
        prepend-icon="search"
        single-line
        v-model="keyword"
        @keyup.enter="sendAddress">
        <font-awesome-icon
          :icon="['fasl', 'magnifying-glass']"
          size="xl"
          class="search-icon" />
      </v-text-field>
      <v-btn icon style="margin-left: 10px">
        <font-awesome-icon
          :icon="['fasl', 'location-crosshairs']"
          size="xl"
          style="color: #838486"
          @click="sendCurPosition" />
      </v-btn>
    </v-toolbar>
    <p v-if="props.validAddress" class="search-warn-info">
      위치를 알 수 없어 기본 위치로 이동합니다.
    </p>
  </div>
</template>

<script setup>
import { defineEmits, defineProps, ref } from "vue";

const props = defineProps({
  validAddress: {
    type: Boolean,
  },
});

const emit = defineEmits(["searchByAddress", "searchCurPosition"]);
const keyword = ref(null);

const sendAddress = () => {
  emit("searchByAddress", keyword.value);
  keyword.value = null;
};

const sendCurPosition = () => {
  emit("searchCurPosition");
};
</script>

<style scoped>
.search-icon {
  color: #838486;
  padding-right: 15px;
}

.search-warn-info {
  width: 60vw;
  background-color: #efefef;
}

@media (max-width: 800px) {
  .search-warn-info {
    width: 100vw;
  }
}
</style>

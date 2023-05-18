<template>
  <div>
    <v-btn-toggle
      v-model="toggle_multiple"
      color="light-green lighten-2"
      multiple
      variant="outlined"
      divided
      @click="selectType"
      style="height: 63px">
      <v-btn value="버드스트라이크"> 버드<br />스트라이크 </v-btn>
      <v-btn value="새발견"> 새발견 </v-btn>
    </v-btn-toggle>
  </div>
</template>
<script setup>
import { ref, defineEmits } from "vue";

const emit = defineEmits(["searchByType"]);
const toggle_multiple = ref([]);

const selectType = () => {
  emit("searchByType", toggle_multiple.value);
};
</script>

<style scoped></style>

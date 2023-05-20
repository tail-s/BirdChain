<template lang="">
  <div class="layout">
  <DonateViewSwiper></DonateViewSwiper>
  <DonateViewDialog></DonateViewDialog>
  <router-view />
  </div>
</template>

<script>
import DonateViewSwiper from "../components/donate/DonateViewSwiper.vue";
import DonateViewDialog from "../components/donate/DonateViewDialog.vue";
export default {
  name: "DonateView",
  components: {
    DonateViewSwiper,
    DonateViewDialog,
  },
};
</script>

<style scoped>
.layout {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100%; 
}
</style>

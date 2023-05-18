<template>
  <div class="mypage">
    <mynft-component></mynft-component>
    <minting-component></minting-component>
  </div>
</template>
<script>
import MintingComponent from "@/components/mypage/MintingComponent.vue";
import MynftComponent from "@/components/mypage/MynftComponent.vue";
export default {
  name: "MypageView",
  components: {
    MynftComponent,
    MintingComponent,
  },
};
</script>
<style>
.mypage{
  background: #F4F4F4;
  padding-bottom: 5vw;
  padding-top: 0.5vw;
}
</style>

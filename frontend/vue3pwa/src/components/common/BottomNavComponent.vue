<template >
  <div id="bottombar">
    <v-layout class="overflow-visible" style="height: 56px;" >
      <v-bottom-navigation
        v-model="value"
        color="teal"
        grow
      >
        
        
        <v-btn style="padding: 10px;" @click="this.$router.push({ name: 'birds' })">
          <v-icon>mdi-bird</v-icon>
          위기 조류
        </v-btn>
        <v-btn style="padding: 10px;" @click="this.$router.push({ name: 'donateView' })">
          <v-icon>mdi-heart</v-icon>
          기부하기
        </v-btn>
        <v-btn style="padding: 10px;" @click="this.$router.push({ name: 'MapView' })">
          <v-icon>mdi-map-marker</v-icon>
          정보공유
        </v-btn>
        <!-- <v-btn style="padding: 10px;" @click="this.$router.push({ name: 'mypage' })">
          <v-icon>mdi-map-marker</v-icon>
          내정보
        </v-btn> -->
        <v-btn style="padding: 10px;" @click="this.$router.push({ name: 'gameView' })">
          <v-icon>mdi-gamepad-variant</v-icon>
          게임
        </v-btn>
      </v-bottom-navigation>
    </v-layout>
  </div>
</template>
<script scoped>
export default {
  name : "BottomNavComponent",
  data: () => ({ value: 1 }),

  
}

</script>
<style>
.footer {
    margin-top: 50px;
}
.v-bottom-navigation__content {
  padding-left: 10px;
  padding-right: 10px;
}

.v-btn--stacked.v-btn--size-default{
  padding: 10px;
}
</style>
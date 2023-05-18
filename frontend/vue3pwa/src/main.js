import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import "./registerServiceWorker";
import router from "./router/index.js";
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import Vue3Swiper from "vue3-swiper";
import "swiper/swiper-bundle.css";
import "@mdi/font/css/materialdesignicons.css";
import Toaster from "@meforma/vue-toaster";

// FontAwesome 관련 임포트
import { library } from "@fortawesome/fontawesome-svg-core";
import { fas } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { useStore } from "@/stores/store";

const vuetify = createVuetify({
  components,
  directives,
});
const pinia = createPinia();
const app = createApp(App);

app.directive("focus", {
  mounted(el, binding) {
    if (binding.value) {
      el.focus();
    }
  },
});

library.add(fas);

// FontAwesome 전역 컴포넌트 등록
app.component("font-awesome-icon", FontAwesomeIcon);
//기부 알림
app.use(Toaster, {
  position: "bottom-right",
});
// app.use(createPinia())
app.use(vuetify).use(router).use(Vue3Swiper).use(pinia);
/* eslint-disable */
const userStore = useStore();
app.mount("#app");

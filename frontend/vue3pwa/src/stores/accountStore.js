import { defineStore } from "pinia";

export const useAccountStore = defineStore({
  id: "account",

  state: () => ({
    account: null,
    hasDonated: false,
  }),
  getters: {
    isConnected: (state) => state.account !== null,
  },
  actions: {
    setAccount(account) {
      this.account = account;
    },

    donate() {
      // 기부 프로세스 수행 (예: 블록체인 기부 트랜잭션 실행)
      // 기부가 완료되면 hasDonated 상태를 true로 설정
      this.hasDonated = true;
    },

    async getAccountAsync() {
      console.log(this.account);
      return await this.account;
    },
  },
});

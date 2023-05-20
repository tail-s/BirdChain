import { defineStore } from 'pinia';

export const countStore = defineStore('count',{
    state: () => ({
        birdcount: 20000,
    }),
    actions: {
        increment() {
            this.birdcount++;
        }
    },
});
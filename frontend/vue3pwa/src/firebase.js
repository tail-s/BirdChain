// firebase.js
import firebase from "firebase/app";
import "firebase/analytics";

const firebaseConfig = {
  apiKey: "AIzaSyALvytQTS03RcChrPI7L2oFSIGamX8akOA",
  authDomain: "birdchain-4d3f9.firebaseapp.com",
  projectId: "birdchain-4d3f9",
  storageBucket: "birdchain-4d3f9.appspot.com",
  messagingSenderId: "207881998711",
  appId: "1:207881998711:web:589f6f9edcfdbe45945172",
  measurementId: "G-V9MW412JNZ",
};

firebase.initializeApp(firebaseConfig);

// Add other Firebase services you want to use, e.g., storage or database

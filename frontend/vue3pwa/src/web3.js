import Web3 from "web3";
import { walletStore } from "@/stores/donationStore";

export async function createWeb3Instance() {
  let web3;
  const wStore = walletStore();

  const setwallet = (value) => {
    wStore.setwallet(value);
  };

  const userAgent = navigator.userAgent;
  const isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(userAgent);


  if (window.ethereum) {
    web3 = new Web3(window.ethereum);
    await window.ethereum.enable();
  } else if (window.web3) {
    web3 = new Web3(window.web3.currentProvider);
  } else if(isMobile){
    alert("기부는 pc 환경에서만 가능합니다.");
  } else {
    alert("MetaMask를 설치하고 로그인한 후 네트워크를 Sepolia로 변경해주세요.");
    window.open("https://metamask.io/");
  }

  try {
    const accounts = await web3.eth.getAccounts();
    setwallet(accounts[0]);
  } catch (e) {
    console.error("지갑을 찾을 수 없음", e);
  }

  return web3;
}

export async function checkWeb3Instance() {
  let web3;
  const wStore = walletStore();

  const setwallet = (value) => {
    wStore.setwallet(value);
  };

  if (window.ethereum) {
    web3 = new Web3(window.ethereum);
    await window.ethereum.enable();
  } else if (window.web3) {
    web3 = new Web3(window.web3.currentProvider);
  } else {
    return null;
  }

  try {
    const accounts = await web3.eth.getAccounts();
    setwallet(accounts[0]);
  } catch (e) {
    console.error("지갑을 찾을 수 없음", e);
  }

  return web3;
}

export async function checkAccountConnection() {
  const web3 = await checkWeb3Instance();
  if (web3 !== null) {
    const accounts = await web3.eth.getAccounts();

    if (accounts.length === 0) {
      return false;
    } else {
      return accounts[0];
    }
  } else {
    return false;
  }
}

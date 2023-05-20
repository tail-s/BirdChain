import { checkAccount } from ".";

// 기부 한 계정 등록 (계정 올리기)
export const donateAccount = async (reqForm) => {
  const res = await checkAccount({
    method: "post",
    url: "",
    data: reqForm,
  });
  return res;
};

// 현재 계정을 가지고 기부한 상태인지 확인 (계정이 정삭적으로 조회 되는지)
export const getCheckoutAccount = async (account) => {
  const res = await checkAccount({
    method: "get",
    url: "",
    params: { wallet: account },
  });
  return res;
};

// 기부 후 NFT 선택 (DB에서 삭제)
export const selectNFT = async (account) => {
  const res = await checkAccount({
    method: "delete",
    url: "",
    params: { wallet: account },
  });
  return res;
};

import { markers } from ".";

// 모든 마커 조회
// 타입별로 마커 조회
export const getMarkersByType = async (marker_type) => {
  const res = await markers({
    method: "get",
    url: "",
    params: {type: marker_type},
  });
  return res;
};

// 마커 상세 조회
export const getMarkerDetail = async (marker_id) => {
  const res = await markers({
    method: "get",
    url: "/detail",
    params: {id: marker_id},
  });
  return res;
};

// 마커 등록
export const registMarker = async (reqForm) => {
  const res = await markers({
    method: "post",
    url: "",
    data: reqForm,
  });
  return res;
};

// 마커 수정
export const modifyMarker = async (reqForm) => {
  const res = await markers({
    method: "put",
    url: "",
    data: reqForm,
  });
  return res;
};

// 마커 삭제
export const deleteMarker = async (reqForm) => {
  const res = await markers({
    method: "put",
    url: "/delete",
    data: reqForm,
  });
  return res;
};

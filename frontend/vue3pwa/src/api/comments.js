import { comments } from ".";

// 댓글 조회
export const getComments = async (marker_id) => {
  const res = await comments({
    method: "get",
    url: "",
    params: {id: marker_id},
  });
  return res;
};

// 댓글 등록
export const registComment = async (reqForm) => {
  const res = await comments({
    method: "post",
    url: "",
    data: reqForm,
  });
  return res;
};

// 댓글 수정
export const modifyComment = async (reqForm) => {
  const res = await comments({
    method: "put",
    url: "",
    data: reqForm,
  });
  return res;
};

// 댓글 삭제
export const deleteComment = async (reqForm) => {
  const res = await comments({
    method: "put",
    url: "/delete",
    data: reqForm,
  });
  return res;
};

package com.ssafy.birdchain.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    /* 400 BAD_REQUEST: 잘못된 요청 */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Invalid Request"),

    /* 401 UNAUTHORIZED: 인증되지 않은 사용자의 요청 */
    UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, "Unauthorized"),

    /* 403 FORBIDDEN: 권한이 없는 사용자의 요청 */
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "Forbidden"),

    /* 404 NOT_FOUND: 리소스를 찾을 수 없음 */
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found"),

    /* 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출 */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Not Allowed Method"),

    /* 500 INTERNAL_SERVER_ERROR: 내부 서버 오류 */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error"),

    // 마커
    MARKER_NOT_FOUND(HttpStatus.NOT_FOUND, "마커가 존재하지 않습니다."),
    MARKER_NOT_ALLOWED(HttpStatus.NOT_ACCEPTABLE, "사용자가 일치하지 않습니다."),

    // 댓글
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),
    COMMENT_NOT_ALLOWED(HttpStatus.NOT_ACCEPTABLE, "사용자가 일치하지 않습니다."),

    // 기부
    DONATION_NOT_FOUND(HttpStatus.NOT_FOUND, "기부 내역이 존재하지 않습니다."),

    // NFT
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "NFT가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
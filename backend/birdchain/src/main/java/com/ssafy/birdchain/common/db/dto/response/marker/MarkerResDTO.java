package com.ssafy.birdchain.common.db.dto.response.marker;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MarkerResDTO {

    @Schema(description = "마커 아이디")
    private Long id;

    @Schema(description = "마커 작성자 닉네임")
    private String nickname;

    @Schema(description = "마커 유형")
    private String type;

    @Schema(description = "마커 경도")
    private Double lng;

    @Schema(description = "마커 위도")
    private Double lat;

    @Schema(description = "마커 위치")
    private String location;

    @Schema(description = "마커 첨부 이미지")
    private String image;

    @Schema(description = "마커 글 내용")
    private String content;

    @Schema(description = "마커 생성일자")
    private LocalDateTime createdAt;
}

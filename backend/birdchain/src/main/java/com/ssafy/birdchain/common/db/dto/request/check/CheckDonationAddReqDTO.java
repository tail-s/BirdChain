package com.ssafy.birdchain.common.db.dto.request.check;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CheckDonationAddReqDTO {

    @Schema(description = "이미지 번호")
    Long imgNum;

    @Schema(description = "기부자 지갑 주소")
    String wallet;

    @Schema(description = "NFT 이미지셋 번호")
    Integer num;

}

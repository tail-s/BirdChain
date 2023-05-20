package com.ssafy.birdchain.common.db.dto.request.donation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class DonationAddReqDTO {

    @Schema(description = "기부 금액")
    Double amount;

    @Schema(description = "기부 트랜잭션 ID")
    String txid;

    @Schema(description = "기부자 지갑 주소")
    String address;

}

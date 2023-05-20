package com.ssafy.birdchain.common.db.dto.response.donation;

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
public class DonationAllResDTO {

    @Schema(description = "기부 ID")
    Long id;

    @Schema(description = "기부 금액")
    Double amount;

    @Schema(description = "기부 트랜잭션 ID")
    String txid;

    @Schema(description = "NFT 이미지 URL")
    String imageUrl;

    @Schema(description = "기부 날짜")
    LocalDateTime createdAt;

}

package com.ssafy.birdchain.common.db.dto.response.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemAllResDTO {

    @Schema(description = "NFT 제목")
    String name;

    @Schema(description = "기부 트랜잭션 ID")
    String txid;

    @Schema(description = "멸종 위기 단계")
    String iucn;

    @Schema(description = "NFT 이미지 URL")
    String imageUrl;

    @Schema(description = "NFT 메타데이터 URL")
    String metadataUrl;

    @Schema(description = "기부 금액")
    Double amount;

}

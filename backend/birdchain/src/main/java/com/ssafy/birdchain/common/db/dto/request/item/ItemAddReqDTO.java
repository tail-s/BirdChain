package com.ssafy.birdchain.common.db.dto.request.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ItemAddReqDTO {

    @Schema(description = "NFT 이미지 URL")
    String imageUrl;

    @Schema(description = "NFT 메타데이터 URL")
    String metadataUrl;

    @Schema(description = "NFT 키워드")
    String iucn;

    @Schema(description = "NFT 새 이름")
    String name;

    @Schema(description = "기부 id")
    Long donationId;

}

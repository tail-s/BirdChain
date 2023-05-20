package com.ssafy.birdchain.api.controller;

import com.ssafy.birdchain.api.service.ItemService;
import com.ssafy.birdchain.common.db.dto.request.item.ItemAddReqDTO;
import com.ssafy.birdchain.common.db.dto.util.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Log4j2
@RestController
@Tag(name = "NFT API")
@RequiredArgsConstructor
@RequestMapping(value = "/items")
public class ItemController {

    private final ItemService itemService;

    /**
     * 보유 NFT 조회 API
     *
     * @param address
     * @return
     */
    @GetMapping
    @Operation(summary = "보유 NFT 작품 조회")
    public ResponseEntity<ResponseDTO> donationAllList(@RequestParam String address) {
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, "조회에 성공했습니다.", itemService.findAllItem(address)));
    }

    /**
     * NFT 상세 조회 API
     *
     * @param id
     * @return
     */
    @GetMapping("/detail")
    @Operation(summary = "NFT 상세 조회")
    public ResponseEntity<ResponseDTO> itemDetail(@RequestParam Long id) {
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, "조회에 성공했습니다.", itemService.findItem(id)));
    }

    /**
     * NFT 선택 시 DB 저장 API
     *
     * @param itemAddReqDTO
     * @return
     * @throws IOException
     */
    @PostMapping
    @Operation(summary = "NFT 선택")
    public ResponseEntity<ResponseDTO> itemAdd(@RequestBody ItemAddReqDTO itemAddReqDTO) {
        itemService.addItem(itemAddReqDTO);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, "등록에 성공했습니다."));
    }

}

package com.ssafy.birdchain.api.controller;

import com.ssafy.birdchain.api.service.CheckDonationService;
import com.ssafy.birdchain.common.db.dto.request.check.CheckDonationAddReqDTO;
import com.ssafy.birdchain.common.db.dto.util.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@Tag(name = "랜덤 이미지 번호 API")
@RequiredArgsConstructor
@RequestMapping(value = "/check")
public class CheckDonationController {

    private final CheckDonationService checkDonationService;

    @GetMapping
    @Operation(summary = "랜덤 이미지 번호 조회")
    public ResponseEntity<ResponseDTO> checkDonationDetail(@RequestParam String wallet) {
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, "조회에 성공했습니다.", checkDonationService.findCheck(wallet)));
    }

    @PostMapping
    @Operation(summary = "랜덤 이미지 번호 등록")
    public ResponseEntity<ResponseDTO> checkDonationAdd(@RequestBody CheckDonationAddReqDTO checkDonationAddReqDTO) {
        checkDonationService.addCheckDonation(checkDonationAddReqDTO);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, "등록에 성공했습니다."));
    }

    @DeleteMapping
    @Operation(summary = "랜덤 이미지 번호 삭제")
    public ResponseEntity<ResponseDTO> checkDonationModify(@RequestParam String wallet) {
        checkDonationService.deleteCheckDonation(wallet);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, "삭제에 성공했습니다."));
    }

}

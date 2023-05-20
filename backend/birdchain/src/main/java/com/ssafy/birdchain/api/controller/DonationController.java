package com.ssafy.birdchain.api.controller;

import com.ssafy.birdchain.api.service.DonationService;
import com.ssafy.birdchain.common.db.dto.request.donation.DonationAddReqDTO;
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
@Tag(name = "기부 API")
@RequiredArgsConstructor
@RequestMapping(value = "/donations")
public class DonationController {

    private final DonationService donationService;

    /**
     * 기부 내역 조회 API
     *
     * @param address
     * @return
     */
    @GetMapping
    @Operation(summary = "기부 내역 조회")
    public ResponseEntity<ResponseDTO> donationAllList(@RequestParam String address) {
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, "조회에 성공했습니다.", donationService.findAllDonation(address)));
    }

    /**
     * 기부 내역 등록 API
     *
     * @param donationAddReqDTO
     * @return
     * @throws IOException
     */
    @PostMapping
    @Operation(summary = "기부 내역 등록")
    public ResponseEntity<ResponseDTO> donationAdd(@RequestBody DonationAddReqDTO donationAddReqDTO) {
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, "등록에 성공했습니다.", donationService.addDonation(donationAddReqDTO)));
    }

}
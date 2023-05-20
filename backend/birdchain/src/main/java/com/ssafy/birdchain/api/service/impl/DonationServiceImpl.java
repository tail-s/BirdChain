package com.ssafy.birdchain.api.service.impl;

import com.ssafy.birdchain.api.service.DonationService;
import com.ssafy.birdchain.common.db.dto.request.donation.DonationAddReqDTO;
import com.ssafy.birdchain.common.db.dto.response.donation.DonationAllResDTO;
import com.ssafy.birdchain.common.db.entity.Donation;
import com.ssafy.birdchain.common.db.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;

    /**
     * 기부 내역 조회
     *
     * @param address
     * @return
     */
    @Override
    public List<DonationAllResDTO> findAllDonation(String address) {
        return donationRepository.findAllByAddress(address);
    }

    /**
     * 기부 내역 등록
     *
     * @param donationAddReqDTO
     */
    @Override
    public Long addDonation(DonationAddReqDTO donationAddReqDTO) {
        Donation donation = Donation.builder()
                .amount(donationAddReqDTO.getAmount())
                .txid(donationAddReqDTO.getTxid())
                .address(donationAddReqDTO.getAddress())
                .build();
        donationRepository.save(donation);
        return donation.getId();
    }

}

package com.ssafy.birdchain.api.service.impl;

import com.ssafy.birdchain.api.service.CheckDonationService;
import com.ssafy.birdchain.common.db.dto.request.check.CheckDonationAddReqDTO;
import com.ssafy.birdchain.common.db.entity.CheckDonation;
import com.ssafy.birdchain.common.db.repository.CheckDonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckDonationServiceImpl implements CheckDonationService {

    private final CheckDonationRepository checkDonationRepository;

    @Override
    public CheckDonation findCheck(String wallet) {
        return checkDonationRepository.findByWallet(wallet);
    }

    @Override
    public void addCheckDonation(CheckDonationAddReqDTO checkDonationAddReqDTO) {
        CheckDonation checkDonation = CheckDonation.builder()
                .imgNum(checkDonationAddReqDTO.getImgNum())
                .wallet(checkDonationAddReqDTO.getWallet())
                .num(checkDonationAddReqDTO.getNum())
                .build();
        checkDonationRepository.save(checkDonation);
    }

    @Override
    public void deleteCheckDonation(String wallet) {
        System.out.println(wallet);
        CheckDonation checkDonation = checkDonationRepository.findByWallet(wallet);
        checkDonationRepository.delete(checkDonation);
    }

}

package com.ssafy.birdchain.api.service;

import com.ssafy.birdchain.common.db.dto.request.check.CheckDonationAddReqDTO;
import com.ssafy.birdchain.common.db.entity.CheckDonation;

public interface CheckDonationService {
    CheckDonation findCheck(String wallet);

    void addCheckDonation(CheckDonationAddReqDTO checkDonationAddReqDTO);

    void deleteCheckDonation(String wallet);

}

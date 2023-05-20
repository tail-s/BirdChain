package com.ssafy.birdchain.api.service;

import com.ssafy.birdchain.common.db.dto.request.donation.DonationAddReqDTO;
import com.ssafy.birdchain.common.db.dto.response.donation.DonationAllResDTO;

import java.util.List;

public interface DonationService {

    List<DonationAllResDTO> findAllDonation(String address);

    Long addDonation(DonationAddReqDTO donationAddReqDTO);

}

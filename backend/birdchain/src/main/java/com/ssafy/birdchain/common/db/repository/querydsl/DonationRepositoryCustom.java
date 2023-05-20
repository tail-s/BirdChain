package com.ssafy.birdchain.common.db.repository.querydsl;

import com.ssafy.birdchain.common.db.dto.response.donation.DonationAllResDTO;

import java.util.List;

public interface DonationRepositoryCustom {

    List<DonationAllResDTO> findAllByAddress(String address);

}

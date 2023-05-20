package com.ssafy.birdchain.common.db.repository.querydsl;

import com.ssafy.birdchain.common.db.dto.response.donation.DonationAllResDTO;
import com.ssafy.birdchain.common.db.dto.response.item.ItemAllResDTO;

import java.util.List;

public interface ItemRepositoryCustom {

    List<ItemAllResDTO> findAllByAddress(String address);

}

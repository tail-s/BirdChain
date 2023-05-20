package com.ssafy.birdchain.api.service.impl;

import com.ssafy.birdchain.api.service.ItemService;
import com.ssafy.birdchain.common.db.dto.request.item.ItemAddReqDTO;
import com.ssafy.birdchain.common.db.dto.response.item.ItemAllResDTO;
import com.ssafy.birdchain.common.db.dto.response.item.ItemResDTO;
import com.ssafy.birdchain.common.db.entity.Donation;
import com.ssafy.birdchain.common.db.entity.Item;
import com.ssafy.birdchain.common.db.repository.DonationRepository;
import com.ssafy.birdchain.common.db.repository.ItemRepository;
import com.ssafy.birdchain.common.exception.CommonApiException;
import com.ssafy.birdchain.common.exception.errorcode.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final DonationRepository donationRepository;

    /* 보유 NFT 목록 조회 API */
    @Override
    public List<ItemAllResDTO> findAllItem(String address) {
        return itemRepository.findAllByAddress(address);
    }

    /* NFT 상세 조회 API */
    @Override
    public ItemResDTO findItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new CommonApiException(CommonErrorCode.ITEM_NOT_FOUND));
        Donation donation = donationRepository.findById(item.getDonation().getId()).orElseThrow(() -> new CommonApiException(CommonErrorCode.DONATION_NOT_FOUND));

        ItemResDTO itemResDTO = ItemResDTO.builder()
                .name(item.getName())
                .iucn(item.getIucn())
                .imageUrl(item.getImageUrl())
                .amount(donation.getAmount())
                .txid(donation.getTxid())
                .createdAt(donation.getCreatedAt())
                .build();
        return itemResDTO;
    }

    /* NFT 선택 API */
    @Override
    public void addItem(ItemAddReqDTO itemAddReqDTO) {
        Donation donation = donationRepository.findById(itemAddReqDTO.getDonationId()).orElseThrow(() -> new CommonApiException(CommonErrorCode.DONATION_NOT_FOUND));
        Item item = Item.builder()
                .name(itemAddReqDTO.getName())
                .iucn(itemAddReqDTO.getIucn())
                .imageUrl(itemAddReqDTO.getImageUrl())
                .metadataUrl(itemAddReqDTO.getMetadataUrl())
                .donation(donation)
                .build();
        itemRepository.save(item);
    }

}

package com.ssafy.birdchain.common.db.repository.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.birdchain.common.db.dto.response.donation.DonationAllResDTO;
import com.ssafy.birdchain.common.db.dto.response.item.ItemAllResDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ssafy.birdchain.common.db.entity.QDonation.donation;
import static com.ssafy.birdchain.common.db.entity.QItem.item;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ItemAllResDTO> findAllByAddress(String address) {
        QueryResults<ItemAllResDTO> results = jpaQueryFactory
                .select(Projections.constructor(ItemAllResDTO.class
                        , item.name
                        , donation.txid
                        , item.iucn
                        , item.imageUrl
                        , item.metadataUrl
                        , donation.amount
                ))
                .from(donation)
                .rightJoin(item)
                .on(donation.id.eq(item.donation.id))
                .where(donation.address.eq(address))
                .fetchResults();
        return results.getResults();
    }

}

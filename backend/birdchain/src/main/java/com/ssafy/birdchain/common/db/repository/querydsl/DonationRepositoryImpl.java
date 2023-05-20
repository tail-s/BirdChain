package com.ssafy.birdchain.common.db.repository.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.birdchain.common.db.dto.response.donation.DonationAllResDTO;
import com.ssafy.birdchain.common.db.entity.QItem;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ssafy.birdchain.common.db.entity.QDonation.donation;
import static com.ssafy.birdchain.common.db.entity.QItem.item;

@RequiredArgsConstructor
public class DonationRepositoryImpl implements DonationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<DonationAllResDTO> findAllByAddress(String address) {
        QueryResults<DonationAllResDTO> results = jpaQueryFactory
                .select(Projections.constructor(DonationAllResDTO.class
                        , donation.id
                        , donation.amount
                        , donation.txid
                        , item.imageUrl
                        , donation.createdAt
                ))
                .from(donation)
                .leftJoin(item)
                .on(donation.id.eq(item.donation.id))
                .where(donation.address.eq(address))
                .fetchResults();
        return results.getResults();
    }

}

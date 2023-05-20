package com.ssafy.birdchain.common.db.repository;

import com.ssafy.birdchain.common.db.entity.CheckDonation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckDonationRepository extends JpaRepository<CheckDonation, Long> {

    CheckDonation findByWallet(String wallet);

}

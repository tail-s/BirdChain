package com.ssafy.birdchain.common.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckDonation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_donation_id", nullable = false)
    private Long id;

    @Column(name = "check_donation_img_num", nullable = false)
    private Long imgNum;

    @Column(name = "check_donation_wallet", nullable = false)
    private String wallet;

    @Column(name = "check_donation_num", nullable = false)
    private Integer num;

}

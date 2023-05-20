package com.ssafy.birdchain.common.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AvailableItem {

    @Id
    @Column(name = "p_id", nullable = false)
    private int id;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;

}

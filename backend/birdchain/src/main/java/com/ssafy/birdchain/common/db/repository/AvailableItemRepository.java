package com.ssafy.birdchain.common.db.repository;

import com.ssafy.birdchain.common.db.entity.AvailableItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailableItemRepository extends JpaRepository<AvailableItem, Integer> {
    List<AvailableItem> findByIsAvailable(boolean isAvailable);
}
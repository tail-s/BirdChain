package com.ssafy.birdchain.common.db.repository;

import com.ssafy.birdchain.common.db.entity.Item;
import com.ssafy.birdchain.common.db.repository.querydsl.ItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

}

package com.ssafy.birdchain.api.service;

import com.ssafy.birdchain.common.db.dto.request.item.ItemAddReqDTO;
import com.ssafy.birdchain.common.db.dto.response.item.ItemAllResDTO;
import com.ssafy.birdchain.common.db.dto.response.item.ItemResDTO;

import java.util.List;

public interface ItemService {

    List<ItemAllResDTO> findAllItem(String address);

    ItemResDTO findItem(Long id);

    void addItem(ItemAddReqDTO itemAddReqDTO);
}

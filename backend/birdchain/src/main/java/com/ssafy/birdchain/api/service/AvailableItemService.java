package com.ssafy.birdchain.api.service;

import java.net.URL;

public interface AvailableItemService {

    int findAvailableItemNumber();

    URL findAvailableItemImageUrl(String fileName);

}

package com.yolotengo.advertisementApp.service;

import com.yolotengo.advertisementApp.model.Advertisement;
import com.yolotengo.advertisementApp.repositories.AdvertisementRepository;
import com.yolotengo.commonLibApp.dto.AdvertisementRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Scope("singleton")
public class AdvertisementService {

    public static final Logger logger = LoggerFactory.getLogger(AdvertisementService.class);

    @Autowired
    AdvertisementRepository advertisementRepository;

    @Autowired
    SerializationService serializationService;

    @Autowired
    CacheService cacheService;

    public Advertisement creationAdvertisement(AdvertisementRequestDTO adrDTO) {
        Advertisement ad = new Advertisement();
        ad.setCreationDate(new Date());
        ad.setItemJason(serializationService.serializer(adrDTO.getItems()));
        ad.setUserId(adrDTO.getUserId());
        ad.setAreaLevel1(adrDTO.getAreaLevel1());
        ad.setAreaLevel2(adrDTO.getAreaLevel2());
        ad.setCategoryId(adrDTO.getCategoryId());
        ad.setPicture(adrDTO.getPicture());
        ad.setLatitue(adrDTO.getLatitue());
        ad.setLongitude(adrDTO.getLongitude());
        ad.setRighNow(adrDTO.isRighNow());
        ad.setDelivery(adrDTO.isDelivery());
        ad = advertisementRepository.save(ad);

        cacheService.putAdvertisementCache(ad);
        return ad;
    }

}

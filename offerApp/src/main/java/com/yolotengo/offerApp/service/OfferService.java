package com.yolotengo.offerApp.service;

import com.yolotengo.commonLibApp.dto.OfferDTO;
import com.yolotengo.offerApp.model.Offer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Scope("singleton")
public class OfferService {

    public static final Logger logger = LoggerFactory.getLogger(OfferService.class);

    @Autowired
    private SerializationService serializationService;

    @Autowired
    private CacheService cacheService;


    public Offer createOffer(OfferDTO offerDTO){
        Offer offer = new Offer();
        offer.setIdAdvertisement(UUID.fromString(offerDTO.getIdAdvertisement()));
        offer.setIdSeller(offerDTO.getIdSeller());
        offer.setIdBuyer(offerDTO.getIdBuyer());
        offer.setItemJason(offerDTO.getItemJason());
        offer.setCreationDate(new Date());
        offer.setSend(offer.isSend());
        offer.setPrice(offerDTO.getPrice());
        offer.setBuyerAccept(offerDTO.isBuyerAccept());

        return null;
    }


}

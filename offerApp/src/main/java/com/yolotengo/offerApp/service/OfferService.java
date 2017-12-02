package com.yolotengo.offerApp.service;

import com.yolotengo.commonLibApp.dto.OfferDTO;
import com.yolotengo.offerApp.model.Offer;
import com.yolotengo.offerApp.repositories.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Scope("singleton")
public class OfferService {

    public static final Logger logger = LoggerFactory.getLogger(OfferService.class);

    @Autowired
    private SerializationService serializationService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private OfferRepository offerRepository;

    public OfferDTO createOffer(OfferDTO offerDTO) throws Exception{
        Offer offer = new Offer();
        offer.setIdAdvertisement(UUID.fromString(offerDTO.getIdAdvertisement()));
        offer.setIdSeller(offerDTO.getIdSeller());
        offer.setIdBuyer(offerDTO.getIdBuyer());
        offer.setItemJason(offerDTO.getItemJason());
        offer.setCreationDate(new Date());
        offer.setSend(offer.isSend());
        offer.setPrice(offerDTO.getPrice());
        offer.setBuyerAccept(false);

        offer = offerRepository.save(offer);
        cacheService.putOfferCache(offer);

        //TODO analice how save amount

        offerDTO.setId(offer.getId().toString());
        return offerDTO;
    }

    public List<OfferDTO> getOfferByIdAdvertisement(String idAdvertisement) throws Exception{

        OfferDTO offerDTO;
        List<OfferDTO> offerDTOList = new ArrayList<>();
        List<Offer> offerList = cacheService.getOfferListCache(idAdvertisement);

        for(Offer offer : offerList){
            offerDTO = new OfferDTO();
            offerDTO.setId(offer.getId().toString());
            offerDTO.setIdAdvertisement(offer.getIdAdvertisement().toString());
            offerDTO.setIdSeller(offer.getIdSeller());
            offerDTO.setIdBuyer(offer.getIdBuyer());
            offerDTO.setItemJason(offer.getItemJason());
            offerDTO.setCreationDate(offer.getCreationDate());
            offerDTO.setSend(offer.isSend());
            offerDTO.setPrice(offer.getPrice());
            offerDTO.setBuyerAccept(offer.isBuyerAccept());
            offerDTOList.add(offerDTO);
        }

        return offerDTOList;
    }


}


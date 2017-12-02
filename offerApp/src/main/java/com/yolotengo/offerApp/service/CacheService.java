package com.yolotengo.offerApp.service;

import com.yolotengo.offerApp.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Scope("singleton")
public class CacheService {

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private SerializationService serializationService;



    public void putOfferCache(Offer offer) {
        String offerSerialice = serializationService.serializer(offer);
        Map<String, String> adMap = new HashMap<>();
        adMap.put(offer.getId().toString(), offerSerialice);

        template.opsForHash().putAll(offer.getIdAdvertisement().toString(), adMap);
    }

    public Offer getOfferByIdCache(String idAdvertisement, String idOffer) {
        Object adObj = template.opsForHash().entries(idAdvertisement).get(idOffer);
        if (adObj == null) return null;
        String advertisementString = adObj.toString();
        Offer offer = serializationService.deserializer(advertisementString, Offer.class);
        return offer;
    }

    public void removeOfferCache(String idAdvertisement, String idOffer){
        template.opsForHash().delete(idAdvertisement, idOffer);
    }

    public List<Offer> getOfferListCache(String idAdvertisement) {
        Offer offer;
        List<Offer> offerList = new ArrayList<>();
        Collection<Object> objectsList = template.opsForHash().entries(idAdvertisement).values();
        for (Object offerObj : objectsList) {
            offer = serializationService.deserializer(offerObj.toString(), Offer.class);
            offerList.add(offer);
        }
        return offerList;
    }

    public void putSellerAdvertisementCache(String sellerId, String idAdvertisement) {
        List<String> idAdvertisementList = new ArrayList<>();
        Collection<Object> objectsList = template.opsForHash().entries(sellerId).values();
        for (Object offerObj : objectsList) {
            idAdvertisementList.add(serializationService.deserializer(offerObj.toString(), String.class));
        }
        idAdvertisementList.add(idAdvertisement);
        template.opsForHash().put(sellerId, String.class,serializationService.serializer(idAdvertisementList));
    }

    public List<String> gettSellerAdvertisementCache(String sellerId) {
        List<String> idAdvertisementList = new ArrayList<>();
        Collection<Object> objectsList = template.opsForHash().entries(sellerId).values();
        for (Object offerObj : objectsList) {
            idAdvertisementList.add(serializationService.deserializer(offerObj.toString(), String.class));
        }
        return idAdvertisementList;
    }

}

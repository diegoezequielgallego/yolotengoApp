package com.yolotengo.advertisementApp.service;

import com.google.gson.Gson;
import com.yolotengo.advertisementApp.model.Advertisement;
import com.yolotengo.advertisementApp.repositories.AdvertisementRepository;
import com.yolotengo.commonLibApp.dto.AdvertisementDTO;
import com.yolotengo.commonLibApp.dto.AdvertisementRequestDTO;
import com.yolotengo.commonLibApp.dto.ItemDTO;
import org.geonames.Toponym;
import org.geonames.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public void test() {
        Advertisement ad = new Advertisement();
        ad.setCreationDate(new Date());
        ad.setItemJason(new Gson().toJson(new ItemDTO("artculo1", "esto es un test")));
        ad.setUserId("Rambo");
        ad.setAreaLevel1("Tablada");
        ad.setAreaLevel2("La Matanza");
        ad.setLatitue(-34.687886);
        ad.setLongitude(-58.529208);

        advertisementRepository.save(ad);
        List<Advertisement> listAdvertisement = advertisementRepository.findByUser("Rambo", 1);

        cacheService.putInCache(ad.getUserId(), listAdvertisement.get(0));
        Advertisement adDeserialice = cacheService.getFromCache("Rambo", Advertisement.class);

        System.out.println("Found key Rambo, value=" + adDeserialice.getUserId());
    }

    public void test2() {
        String key = "Rambo";
        System.out.println("Found key " + key + ", value=" + cacheService.getFromCache(key, Advertisement.class));

        WebService.setUserName("diegoezequielgallego");
        List<Toponym> aux;
        try {
            //lat log radio y cant
            aux = WebService.findNearbyPlaceName(-34.687886, -58.529208,5.0, 20);
            System.out.println(aux.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

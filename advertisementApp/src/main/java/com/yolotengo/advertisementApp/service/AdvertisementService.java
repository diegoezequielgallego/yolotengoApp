package com.yolotengo.advertisementApp.service;

import com.yolotengo.advertisementApp.model.Advertisement;
import com.yolotengo.advertisementApp.repositories.AdvertisementRepository;
import com.yolotengo.commonLibApp.dto.AdvertisementDTO;
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
    CacheService cacheService;

    public void test() {
        AdvertisementDTO dto = new AdvertisementDTO();

        Advertisement ad = new Advertisement();
        ad.setCreationDate(new Date());
        ad.setDescription("esto es un test");
        ad.setUser("Rambo");
        advertisementRepository.save(ad);
        List<Advertisement> listAdvertisement = advertisementRepository.findByUser("Rambo", 1);

        cacheService.putInCache(ad.getUser(), listAdvertisement.get(0));

        Advertisement adDeserialice = cacheService.getFromCache("Rambo", Advertisement.class);

        System.out.println("Found key Rambo, value=" + adDeserialice.getUser());

    }

    public void test2() {
        String key = "Rambo";
        System.out.println("Found key " + key + ", value=" + cacheService.getFromCache(key,Advertisement.class));
    }

    public void creationAdvertisement(AdvertisementDTO adDTO) {

    }
}

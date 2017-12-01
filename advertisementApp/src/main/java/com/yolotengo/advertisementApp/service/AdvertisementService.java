package com.yolotengo.advertisementApp.service;

import com.yolotengo.advertisementApp.model.Advertisement;
import com.yolotengo.advertisementApp.model.UserAdvertisement;
import com.yolotengo.advertisementApp.repositories.AdvertisementRepository;
import com.yolotengo.advertisementApp.repositories.UserAdvertisementRepository;
import com.yolotengo.commonLibApp.dto.AdvertisementDTO;
import com.yolotengo.commonLibApp.dto.FilterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope("singleton")
public class AdvertisementService {

    public static final Logger logger = LoggerFactory.getLogger(AdvertisementService.class);

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private UserAdvertisementRepository userAdvertisementRepository;

    @Autowired
    private SerializationService serializationService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private GeoLocationService geoLocationService;

    public AdvertisementDTO creationAdvertisement(AdvertisementDTO adDTO) {
        Advertisement ad = new Advertisement();
        ad.setCreationDate(new Date());
        ad.setItemJason(serializationService.serializer(adDTO.getItems()));
        ad.setUserId(adDTO.getUserId());
        ad.setAreaLevel1(adDTO.getAreaLevel1());
        ad.setAreaLevel2(adDTO.getAreaLevel2());
        ad.setCategoryId(adDTO.getCategoryId());
        ad.setPicture(adDTO.getPicture());
        ad.setLatitue(adDTO.getLatitude());
        ad.setLongitude(adDTO.getLongitude());
        ad.setRighNow(adDTO.isRighNow());
        ad.setDelivery(adDTO.isDelivery());
        ad = advertisementRepository.save(ad);

        UserAdvertisement userAd = new UserAdvertisement();
        userAd.setIdAdvertisement(ad.getId());
        userAd.setUserId(adDTO.getUserId());
        userAd.setReposted(false);
        userAdvertisementRepository.save(userAd);

        adDTO.setId(ad.getId().toString());
        cacheService.putAdvertisementCache(ad);
        logger.warn("create new Advertisement, id:" + ad.getId());
        return adDTO;
    }


    public List<AdvertisementDTO> getAdvertisementByUser(String userId) {
        List<UserAdvertisement> userAdList = userAdvertisementRepository.findByUser(userId);
        List<AdvertisementDTO> advertisementDTOList = new ArrayList<>();
        AdvertisementDTO adDTO;
        for (UserAdvertisement userAd : userAdList) {
            Advertisement advertisement = advertisementRepository.findById(userAd.getIdAdvertisement());
            adDTO = new AdvertisementDTO();
            adDTO.setId(advertisement.getId().toString());
            adDTO.setCreationDate(advertisement.getCreationDate());
            adDTO.setAreaLevel1(advertisement.getAreaLevel1());
            adDTO.setUserId(advertisement.getUserId());
            adDTO.setItems(serializationService.deserializer(advertisement.getItemJason(), ArrayList.class));
            adDTO.setCategoryId(advertisement.getCategoryId());
            adDTO.setPicture(advertisement.getPicture());
            adDTO.setDelivery(advertisement.isDelivery());
            adDTO.setRighNow(advertisement.isRighNow());
            advertisementDTOList.add(adDTO);
        }

        return advertisementDTOList;
    }


    public void removeAdvertisement(Advertisement ad) {
        advertisementRepository.delete(ad);
        cacheService.removeAdvertisementCache(ad.getAreaLevel1(), ad.getId().toString());
    }

    public List<AdvertisementDTO> getNerbyAdvertisement(FilterDTO filter) throws Exception {
        AdvertisementDTO adDTO;
        List<AdvertisementDTO> adDTOList;
        List<String> cityList;

        cityList = cacheService.getNearbyCityListCache(String.valueOf(filter.getRatio()), filter.getAreaLevel());

        if (cityList == null) {
            logger.warn("no found in cache key: " + filter.getRatio() + " subkey: " + filter.getAreaLevel());
            cityList = geoLocationService.getNearbyPlace(filter.getLatitude(),
                    filter.getLongitude(), filter.getRatio());
            cacheService.putNearbyCityListCache(String.valueOf(filter.getRatio()), filter.getAreaLevel(), cityList);
        }

        adDTOList = new ArrayList<>();
        List<Advertisement> advertisementCacheList;
        for (String city : cityList) {
            advertisementCacheList = cacheService.getAdvertisementListCache(city);
            for (Advertisement advertisement : advertisementCacheList) {

                if (advertisement.isRighNow() != filter.isRighNow()) {
                    continue;
                }
                if (advertisement.isDelivery() != filter.isDelivery()) {
                    continue;
                }
                if (!advertisement.getCategoryId().equals(filter.getCategoryID())) {
                    continue;
                }
                Double distance = geoLocationService.calculateDistance(filter.getLatitude(),
                        advertisement.getLatitue(), filter.getLongitude(), advertisement.getLongitude());

                if (distance.compareTo(new Double(filter.getRatio() * 1000)) == -1) {
                    adDTO = new AdvertisementDTO();
                    adDTO.setId(advertisement.getId().toString());
                    adDTO.setCreationDate(advertisement.getCreationDate());
                    adDTO.setAreaLevel1(advertisement.getAreaLevel1());
                    adDTO.setUserId(advertisement.getUserId());
                    adDTO.setItems(serializationService.deserializer(advertisement.getItemJason(), ArrayList.class));
                    adDTO.setCategoryId(advertisement.getCategoryId());
                    adDTO.setDistance(distance);
                    adDTO.setPicture(advertisement.getPicture());
                    adDTO.setDelivery(advertisement.isDelivery());
                    adDTO.setRighNow(advertisement.isRighNow());
                    adDTOList.add(adDTO);
                }
            }
        }

        return adDTOList;
    }
}

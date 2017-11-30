package com.yolotengo.advertisementApp.service;

import com.yolotengo.advertisementApp.model.Advertisement;
import com.yolotengo.advertisementApp.repositories.AdvertisementRepository;
import com.yolotengo.commonLibApp.dto.AdvertisementDTO;
import com.yolotengo.commonLibApp.dto.AdvertisementRequestDTO;
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
    private SerializationService serializationService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private GeoLocationService geoLocationService;

    public Advertisement creationAdvertisement(AdvertisementRequestDTO adrDTO) {
        Advertisement ad = new Advertisement();
        ad.setCreationDate(new Date());
        ad.setItemJason(serializationService.serializer(adrDTO.getItems()));
        ad.setUserId(adrDTO.getUserId());
        ad.setAreaLevel1(adrDTO.getAreaLevel1());
        ad.setAreaLevel2(adrDTO.getAreaLevel2());
        ad.setCategoryId(adrDTO.getCategoryId());
        ad.setPicture(adrDTO.getPicture());
        ad.setLatitue(adrDTO.getLatitude());
        ad.setLongitude(adrDTO.getLongitude());
        ad.setRighNow(adrDTO.isRighNow());
        ad.setDelivery(adrDTO.isDelivery());
        ad = advertisementRepository.save(ad);

        cacheService.putAdvertisementCache(ad);
        logger.warn("create new Advertisement, id:" + ad.getId());
        return ad;
    }


    public List<AdvertisementDTO> getNerbyAdvertisement(FilterDTO filter) throws Exception {
        AdvertisementDTO adDTO;
        List<AdvertisementDTO> adDTOList;
        List<String> cityList;

        cityList = cacheService.getNearbyCityListCache(String.valueOf(filter.getRatio()), filter.getAreaLevel());

        if (cityList == null) {
            logger.warn("no found in cache key: " + filter.getRatio() + " subkey: " +filter.getAreaLevel());
            cityList = geoLocationService.getNearbyPlace(filter.getLatitude(),
                    filter.getLongitude(), filter.getRatio());
            cacheService.putNearbyCityListCache(cityList, String.valueOf(filter.getRatio()), filter.getAreaLevel());
        }

        adDTOList = new ArrayList<>();
        List<Advertisement> advertisementCacheList;
        for (String city : cityList) {
            advertisementCacheList = cacheService.getAdvertisementListCache(city);
            for (Advertisement advertisement: advertisementCacheList){
                Double distance = geoLocationService.calculateDistance(filter.getLatitude(),
                        advertisement.getLatitue(), filter.getLongitude(), advertisement.getLongitude());

                if (distance.compareTo(new Double(filter.getRatio()*1000)) == -1){
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

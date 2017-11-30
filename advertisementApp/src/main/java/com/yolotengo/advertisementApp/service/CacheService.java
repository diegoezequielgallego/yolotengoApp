package com.yolotengo.advertisementApp.service;

import com.yolotengo.advertisementApp.model.Advertisement;
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

    public void putAdvertisementCache(Advertisement ad) {
        String adSerialice = serializationService.serializer(ad);
        Map<String, String> adMap = new HashMap<>();
        adMap.put(ad.getId().toString(), adSerialice);

        template.opsForHash().putAll(ad.getAreaLevel1(), adMap);
    }

    public Advertisement getAdvertisementCache(String areaKey, String id) {
        String advertisementString = template.opsForHash().entries(areaKey).get(id).toString();
        Advertisement advertisement = serializationService.deserializer(advertisementString, Advertisement.class);
        return advertisement;
    }

    public void removeAdvertisementCache(String areaKey, String id) {
        template.opsForHash().entries(areaKey).remove(id);
    }

    public void removeNearbyPlaceCache(String primaryKey) {
        template.delete("ratio-" + primaryKey + "km");
    }

    public List<Advertisement> getAdvertisementListCache(String areaKey) {
        Advertisement advertisement;
        List<Advertisement> advertisementList = new ArrayList<>();
        Collection<Object> objectsList = template.opsForHash().entries(areaKey).values();
        for (Object advertisementObj : objectsList) {
            advertisement = serializationService.deserializer(advertisementObj.toString(), Advertisement.class);
            advertisementList.add(advertisement);
        }
        return advertisementList;
    }

    public List<String> getNearbyCityListCache(String ratioKey, String areaKey) {
        Object cityObj = template.opsForHash().entries("ratio-" + ratioKey+"km").get(areaKey);
        if (cityObj == null) return null;
        String cityListString = cityObj.toString();
        return serializationService.deserializer(cityListString, ArrayList.class);
    }

    public void putNearbyCityListCache(List<String> cityList, String ratioKey, String areaLeveKey) {
        String cityListSerialice = serializationService.serializer(cityList);
        Map<String, String> cityMap = new HashMap<>();
        cityMap.put(areaLeveKey, cityListSerialice);

        template.opsForHash().putAll("ratio-"+ratioKey+"km", cityMap);
    }
}

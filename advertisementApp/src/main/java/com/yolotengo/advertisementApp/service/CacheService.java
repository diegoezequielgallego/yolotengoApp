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
        String cityListString = template.opsForHash().entries(ratioKey).get(areaKey).toString();
        return serializationService.deserializer(cityListString, ArrayList.class);
    }

}

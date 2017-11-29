package com.yolotengo.advertisementApp.service;

import com.yolotengo.advertisementApp.model.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Scope("singleton")
public class CacheService {

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private SerializationService serializationService;

    public void putInCache(String key, Object obj) {
        String objSerialice = serializationService.serializer(obj);
        template.opsForValue().set(key, objSerialice);
    }

    public <T> T getFromCache(String key, Class<T> objClass) {
        return serializationService.deserializer(template.opsForValue().get(key), objClass);
    }

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

}

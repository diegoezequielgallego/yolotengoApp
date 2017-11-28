package com.yolotengo.advertisementApp.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class CacheService {

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private SerializationService serializationService;

    public void putInCache(String key, Object obj){
        String objSerialice= serializationService.serializer(obj);
        template.opsForValue().set(key, objSerialice);
    }

    public  <T> T getFromCache(String key, Class<T> objClass){
        return serializationService.deserializer(template.opsForValue().get(key),objClass);
    }

}

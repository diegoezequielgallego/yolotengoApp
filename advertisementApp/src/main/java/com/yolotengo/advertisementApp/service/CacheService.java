package com.yolotengo.advertisementApp.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class CacheService {

    private Gson gson;

    @Autowired
    private StringRedisTemplate template;

    public void putInCache(String key, Object obj){
        String objSerialice= serializer(obj);
        template.opsForValue().set(key, objSerialice);
    }

    public  <T> T getFromCache(String key, Class<T> objClass){
        return deserializer(template.opsForValue().get(key),objClass);
    }


    private String serializer(Object obj){
        return getGson().toJson(obj);
    }

    private <T> T deserializer(String obj, Class<T> objClass){
        return getGson().fromJson(obj, objClass);
    }

    private Gson getGson(){
        if (gson == null){
            gson = new Gson();
        }
        return gson;
    }

}

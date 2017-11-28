package com.yolotengo.advertisementApp.service;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by dgallego on 28/11/2017.
 */

@Service
@Scope("singleton")
public class SerializationService {

    private Gson gson;

    public String serializer(Object obj){
        return getGson().toJson(obj);
    }

    public <T> T deserializer(String obj, Class<T> objClass){
        return getGson().fromJson(obj, objClass);
    }

    public Gson getGson(){
        if (gson == null){
            gson = new Gson();
        }
        return gson;
    }

}

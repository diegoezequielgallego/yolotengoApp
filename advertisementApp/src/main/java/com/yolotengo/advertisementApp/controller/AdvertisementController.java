package com.yolotengo.advertisementApp.controller;

import com.yolotengo.advertisementApp.service.AdvertisementService;
import com.yolotengo.commonLibApp.dto.AdvertisementRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * Created by dgallego on 23/11/2017.
 */
@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {

    public static final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);

    @Autowired
    AdvertisementService advertisementService;

    @Autowired
    private StringRedisTemplate template;

    @RequestMapping(value = "/creation/advertisement", method = RequestMethod.POST)
    public ResponseEntity<?> creationAdvertisement(@PathParam("adDTO") AdvertisementRequestDTO adrDTO) {
        try {
            advertisementService.creationAdvertisement(adrDTO);
            return ResponseEntity.ok(HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.ok(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}

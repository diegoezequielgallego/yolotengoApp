package com.yolotengo.offerApp.controller;

import com.yolotengo.commonLibApp.dto.OfferDTO;
import com.yolotengo.offerApp.service.OfferService;
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
@RequestMapping("/offer")
public class OfferController {

    public static final Logger logger = LoggerFactory.getLogger(OfferController.class);

    @Autowired
    OfferService offerService;

    @Autowired
    private StringRedisTemplate template;

    @RequestMapping(value = "/creation/offer", method = RequestMethod.POST)
    public ResponseEntity<?> creationAdvertisement(@PathParam("adDTO") OfferDTO offerDTO) {
        try {

            return ResponseEntity.ok(HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.ok(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}

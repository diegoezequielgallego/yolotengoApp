package com.yolotengo.advertisementApp.controller;

import com.yolotengo.advertisementApp.service.AdvertisementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dgallego on 23/11/2017.
 */
@RestController
@RequestMapping("/main")
public class AdvertisementController {

    public static final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);

    @Autowired
    AdvertisementService advertisementService;

    @Autowired
    private StringRedisTemplate template;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseEntity<?> test() {
        logger.debug("init test");
        advertisementService.test();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    public ResponseEntity<?> test2() {
        advertisementService.test2();
        return ResponseEntity.ok(HttpStatus.OK);
    }

}

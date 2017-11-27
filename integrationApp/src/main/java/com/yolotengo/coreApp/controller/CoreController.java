package com.yolotengo.coreApp.controller;

import com.yolotengo.coreApp.service.CoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CoreController {

    public static final Logger logger = LoggerFactory.getLogger(CoreController.class);

    @Autowired
    CoreService coreService;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseEntity<?> test() {
        logger.debug("init test");
        coreService.test();
        return ResponseEntity.ok(HttpStatus.OK);
    }

}

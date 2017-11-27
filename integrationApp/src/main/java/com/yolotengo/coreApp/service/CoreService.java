package com.yolotengo.coreApp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class CoreService {

    public static final Logger logger = LoggerFactory.getLogger(CoreService.class);

    public void test() {
        logger.debug("llego");


    }


}

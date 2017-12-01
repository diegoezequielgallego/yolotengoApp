package com.yolotengo.offerApp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class OfferService {

    public static final Logger logger = LoggerFactory.getLogger(OfferService.class);

    @Autowired
    private SerializationService serializationService;

    @Autowired
    private CacheService cacheService;



}

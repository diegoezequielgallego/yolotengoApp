package com.yolotengo.offerApp.test;

import com.yolotengo.offerApp.SpringBootCRUDApp;
import com.yolotengo.offerApp.configuration.CassandraConfig;
import com.yolotengo.offerApp.configuration.SpringRedisConfig;
import com.yolotengo.offerApp.repositories.OfferRepository;
import com.yolotengo.offerApp.service.OfferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.support.BasicMapId;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootCRUDApp.class, CassandraConfig.class, SpringRedisConfig.class})
public class OfferAppTest {

    public static final Logger logger = LoggerFactory.getLogger(OfferAppTest.class);


    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    OfferService offerService;





}

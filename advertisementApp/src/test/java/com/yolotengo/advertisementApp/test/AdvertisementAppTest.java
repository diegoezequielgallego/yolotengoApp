package com.yolotengo.advertisementApp.test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.RegularStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.utils.UUIDs;
import com.yolotengo.advertisementApp.SpringBootCRUDApp;
import com.yolotengo.advertisementApp.configuration.CassandraConfig;
import com.yolotengo.advertisementApp.configuration.SpringRedisConfig;
import com.yolotengo.advertisementApp.model.Advertisement;
import com.yolotengo.advertisementApp.repositories.AdvertisementRepository;
import com.yolotengo.advertisementApp.service.AdvertisementService;
import com.yolotengo.advertisementApp.service.SerializationService;
import com.yolotengo.commonLibApp.dto.AdvertisementRequestDTO;
import com.yolotengo.commonLibApp.dto.ItemDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootCRUDApp.class, CassandraConfig.class, SpringRedisConfig.class})
public class AdvertisementAppTest {

    public static final Logger logger = LoggerFactory.getLogger(AdvertisementAppTest.class);

    @Autowired
    AdvertisementService advertisementService;

    @Autowired
    AdvertisementRepository advertisementRepository;

    @Autowired
    SerializationService serializationService;

    @Test
    public void testCreationAdvertisement() {
        AdvertisementRequestDTO adrDTO = new AdvertisementRequestDTO();
        adrDTO.setUserId("rambo");
        adrDTO.setCategoryId("servicios");
        adrDTO.setItems(new ArrayList<>(Arrays.asList(new ItemDTO("machete", "quiero un machete"))));
        adrDTO.setPicture("/picture");
        adrDTO.setCreationDate(new Date());
        adrDTO.setAreaLevel1("Tablada");
        adrDTO.setAreaLevel2("La Matanza");
        adrDTO.setLatitue(-34.687886);
        adrDTO.setLongitude(-58.529208);
        adrDTO.setRighNow(true);
        adrDTO.setDelivery(true);

        Advertisement ad = advertisementService.creationAdvertisement(adrDTO);
        String advertisementId = ad.getId().toString();
        advertisementRepository.delete(ad);
        Assert.isTrue(advertisementId != null);

    }


    @Test
    public void testBulkCreationAdvertisement() {
        Random r;
        Cluster cluster;
        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        Session session = cluster.connect();
        Batch batch = QueryBuilder.unloggedBatch();


        double rangeMinLatitue = -34.453143;
        double rangeMaxLatitue = -34.788205;
        double rangeMinLongitude = -58.215195;
        double rangeMaxLongitude = -58.695443;
        double randomValueLatitue;
        double randomValueLongitude;

        advertisementRepository.deleteAll();
        String items = serializationService.serializer(new ArrayList<>(Arrays.asList(new ItemDTO("machete", "quiero un machete"))));

        for (long i = 0; i < 1000; i++) {

            r = new Random();
            randomValueLatitue = rangeMinLatitue + (rangeMaxLatitue - rangeMinLatitue) * r.nextDouble();
            r = new Random();
            randomValueLongitude = rangeMinLongitude + (rangeMaxLongitude - rangeMinLongitude) * r.nextDouble();


            RegularStatement insert = QueryBuilder.insertInto("advertisement_keyspace", "advertisement").values(
                    new String[]{"id", "creationDate", "areaLevel1", "areaLevel2", "userId", "itemJason"
                            , "categoryId", "picture", "latitue", "longitude", "righNow", "delivery"},
                    new Object[]{UUIDs.timeBased(), new Date(), "Tablada", "La Matanza"
                            , "Rambo", items, "servicios", "/picture", randomValueLatitue, randomValueLongitude, true, true});
            insert.setConsistencyLevel(ConsistencyLevel.ONE);
            batch.add(insert);


            if (i % 100 == 0) {
                session.execute(batch);
                batch = QueryBuilder.unloggedBatch();
                logger.warn("create advertisement number:" + i);
            }
        }


        //Long countAdvertisement = advertisementRepository.count();
        //System.out.println(countAdvertisement);
        //Assert.isTrue(countAdvertisement.equals(1000000L));
    }


    @Test
    public void countBulkCreationAdvertisement() {
        //Long countAdvertisement = advertisementRepository.count();
        //System.out.println(countAdvertisement);
        double rangeMinLatitue = -34.553143;
        double rangeMaxLatitue = -34.500205;

        List<Advertisement> list = advertisementRepository.findNearby(rangeMinLatitue, rangeMaxLatitue);
        System.out.println(list.size());

    }

}

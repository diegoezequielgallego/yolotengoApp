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
import com.yolotengo.advertisementApp.service.CacheService;
import com.yolotengo.advertisementApp.service.GeoLocationService;
import com.yolotengo.advertisementApp.service.SerializationService;
import com.yolotengo.commonLibApp.dto.AdvertisementDTO;
import com.yolotengo.commonLibApp.dto.AdvertisementRequestDTO;
import com.yolotengo.commonLibApp.dto.ItemDTO;
import com.yolotengo.commonLibApp.dto.FilterDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;

import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootCRUDApp.class, CassandraConfig.class, SpringRedisConfig.class})
public class AdvertisementAppTest {

    public static final Logger logger = LoggerFactory.getLogger(AdvertisementAppTest.class);

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private SerializationService serializationService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private GeoLocationService geoLocationService;

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
        adrDTO.setLatitude(-34.687886);
        adrDTO.setLongitude(-58.529208);
        adrDTO.setRighNow(true);
        adrDTO.setDelivery(true);

        Advertisement ad = advertisementService.creationAdvertisement(adrDTO);

        String advertisementId = ad.getId().toString();
        String advertisementArea = ad.getAreaLevel1();

        Assert.notNull(advertisementId, "");

        ad = cacheService.getAdvertisementCache(advertisementArea, advertisementId);
        Assert.notNull(ad, "");

        advertisementRepository.delete(ad);
        cacheService.removeAdvertisementCache(advertisementArea, advertisementId);

        ad = cacheService.getAdvertisementCache(advertisementArea, advertisementId);
        Assert.isTrue(ad == null, "");

    }


    @Test
    public void testBulkCreationAdvertisement() throws Exception {
        Random r;
        Cluster cluster;
        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        Session session = cluster.connect();
        Batch batch = QueryBuilder.unloggedBatch();

        //Clear All Data
        advertisementRepository.deleteAll();
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushAll();


        double rangeMinLatitue = -34.453143;
        double rangeMaxLatitue = -34.788205;
        double rangeMinLongitude = -58.215195;
        double rangeMaxLongitude = -58.695443;
        double randomValueLatitue;
        double randomValueLongitude;

        Advertisement ad = new Advertisement();
        ad.setUserId("rambo");
        ad.setCategoryId("servicios");
        List itemList = new ArrayList<>(Arrays.asList(new ItemDTO("machete", "quiero un machete")));
        ad.setItemJason(serializationService.serializer(itemList));
        ad.setPicture("/picture");
        ad.setCreationDate(new Date());
        ad.setAreaLevel2("La Matanza");
        ad.setRighNow(true);
        ad.setDelivery(true);


        //lat log radio y cant
        List<String> cityList = geoLocationService.getNearbyPlace(-34.687886, -58.529208, 30.0);

        for (long i = 0; i <= 10000; i++) {

            r = new Random();
            randomValueLatitue = rangeMinLatitue + (rangeMaxLatitue - rangeMinLatitue) * r.nextDouble();
            r = new Random();
            randomValueLongitude = rangeMinLongitude + (rangeMaxLongitude - rangeMinLongitude) * r.nextDouble();

            int randomCity = 0 + (int) (Math.random() * cityList.size());

            UUID id = UUIDs.timeBased();

            RegularStatement insert = QueryBuilder.insertInto("advertisement_keyspace", "advertisement").values(
                    new String[]{"id", "creationDate", "areaLevel1", "areaLevel2", "userId", "itemJason"
                            , "categoryId", "picture", "latitue", "longitude", "righNow", "delivery"},
                    new Object[]{id, new Date(), cityList.get(randomCity), "La Matanza"
                            , "Rambo", serializationService.serializer(itemList), "servicios", "/picture",
                            randomValueLatitue, randomValueLongitude, true, true});
            insert.setConsistencyLevel(ConsistencyLevel.ONE);
            batch.add(insert);

            if (i % 100 == 0) {
                session.execute(batch);
                batch = QueryBuilder.unloggedBatch();
                logger.warn("create advertisement number:" + i);
            }

            ad.setId(id);
            ad.setAreaLevel1(cityList.get(randomCity));
            ad.setLatitue(randomValueLatitue);
            ad.setLongitude(randomValueLongitude);

            cacheService.putAdvertisementCache(ad);
        }
    }


    @Test
    public void testConsistenceCassandraAndRedis() {
        List<String> cityList = new ArrayList<>();
        cityList.add("Tapiales");
        cityList.add("Tablada");
        cityList.add("Ramos Mejía");
        cityList.add("Mataderos");
        cityList.add("Aldo Bonzi");
        Long start;
        Long end;

        start = System.currentTimeMillis();
        List<Advertisement> advertisementDDBBList = new ArrayList<>();
        for (String city : cityList) {
            advertisementDDBBList.addAll(advertisementRepository.findByPlace(city));
        }
        end = System.currentTimeMillis();
        logger.warn("result found cassandra: " + advertisementDDBBList.size());
        logger.warn("time cassandra: " + (end - start));

        start = System.currentTimeMillis();
        List<Advertisement> advertisementCacheList = new ArrayList<>();
        for (String city : cityList) {
            advertisementCacheList.addAll(cacheService.getAdvertisementListCache(city));
        }
        end = System.currentTimeMillis();
        logger.warn("result found redis: " + advertisementCacheList.size());
        logger.warn("time redis: " + (end - start));


        Assert.isTrue(advertisementCacheList.size() == advertisementDDBBList.size(), "");
    }

    @Test
    public void testDistanceCalculator() {
        Double homeLat = -34.686872;
        Double homeLon = -58.530553;
        Double uncleLat = -34.688277;
        Double uncleLon = -58.528735;

        Double distance = geoLocationService.calculateDistance(homeLat, uncleLat, homeLon, uncleLon);
        logger.warn("distance Diego s home to Uncle: " + distance);
        Assert.isTrue(distance.compareTo(new Double("300")) == -1, "");

    }

    @Test
    public void testGetNerbyAdvertisement() throws Exception {
        FilterDTO location = new FilterDTO();
        location.setLatitude(-34.687886);
        location.setLongitude(-58.529208);
        location.setRatio(5);
        List<AdvertisementDTO> AdvertisementList = advertisementService.getNerbyAdvertisement(location);

    }


}

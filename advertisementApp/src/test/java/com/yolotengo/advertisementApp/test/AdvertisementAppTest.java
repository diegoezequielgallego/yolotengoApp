package com.yolotengo.advertisementApp.test;

import com.yolotengo.advertisementApp.SpringBootCRUDApp;
import com.yolotengo.advertisementApp.configuration.CassandraConfig;
import com.yolotengo.advertisementApp.configuration.SpringRedisConfig;
import com.yolotengo.advertisementApp.service.AdvertisementService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ SpringBootCRUDApp.class, CassandraConfig.class, SpringRedisConfig.class})
public class AdvertisementAppTest {

	@Autowired
	AdvertisementService advertisementService;

	@Test
	public void testCreationAdvertisement() {
		advertisementService.creationAdvertisement(null);
		System.out.println("llego");
	}

	
}

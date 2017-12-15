package com.yolotengo.gatewayApp.test;

import com.yolotengo.gatewayApp.SpringBootCRUDApp;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yolotengo.gatewayApp.configuration.JpaConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ SpringBootCRUDApp.class, JpaConfiguration.class })
public class AppTest {
	

}

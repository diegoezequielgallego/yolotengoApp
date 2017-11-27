package com.yolotengo.advertisementApp;

import com.yolotengo.advertisementApp.configuration.CassandraConfig;
import com.yolotengo.advertisementApp.configuration.SpringRedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Import({CassandraConfig.class,SpringRedisConfig.class})
@SpringBootApplication(scanBasePackages={"com.yolotengo.advertisementApp"})
// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class SpringBootCRUDApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCRUDApp.class, args);
	}
}

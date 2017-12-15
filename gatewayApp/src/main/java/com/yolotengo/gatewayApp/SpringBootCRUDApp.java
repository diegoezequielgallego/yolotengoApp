package com.yolotengo.gatewayApp;

import com.yolotengo.gatewayApp.configuration.JpaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;


@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages = {"com.yolotengo.gatewayApp"})
public class SpringBootCRUDApp extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootCRUDApp.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCRUDApp.class, args);
    }
}

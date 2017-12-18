package com.yolotengo.gatewayApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        System.out.println("llego");
    }

}

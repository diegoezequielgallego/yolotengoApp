package com.yolotengo.gatewayApp.service;

import com.yolotengo.gatewayApp.controller.RestApiController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class MutantService {
	
	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);


}

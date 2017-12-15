package com.yolotengo.gatewayApp.service;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import com.yolotengo.gatewayApp.controller.RestApiController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class UserService {
	
	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);


	public void isUserExists(String token){

		FacebookClient fbClient = new DefaultFacebookClient(token);
		User me = fbClient.fetchObject("me", User.class);

		System.out.println(me.getEmail());

	}

}

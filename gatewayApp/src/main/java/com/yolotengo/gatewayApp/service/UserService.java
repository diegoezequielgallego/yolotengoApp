package com.yolotengo.gatewayApp.service;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.User;
import com.yolotengo.gatewayApp.controller.LogInController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class UserService {
	
	public static final Logger logger = LoggerFactory.getLogger(LogInController.class);


	public void isUserExists(String token){

		FacebookClient fbClient = new DefaultFacebookClient(token, Version.VERSION_2_5);
		User me = fbClient.fetchObject("me", User.class, Parameter.with("fields", "email,first_name,last_name,gender"));

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(me.getLastName(), null, null);
		SecurityContextHolder.getContext().setAuthentication(authentication);


		System.out.println(me.getEmail());

	}

}

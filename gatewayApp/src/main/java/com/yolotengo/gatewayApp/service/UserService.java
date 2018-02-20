package com.yolotengo.gatewayApp.service;

import com.yolotengo.gatewayApp.controller.LogInController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Scope("singleton")
public class UserService {
	
	public static final Logger logger = LoggerFactory.getLogger(LogInController.class);


	public void isUserExists(String token){

		//FacebookClient fbClient = new DefaultFacebookClient(token, Version.VERSION_2_5);
		//User me = fbClient.fetchObject("me", User.class, Parameter.with("fields", "email,first_name,last_name,gender"));

		Facebook facebook = new FacebookTemplate(token);
		//String email = facebook.userOperations().getUserProfile().getEmail();
		String [] fields = { "id","name","birthday","email","location","hometown","gender","first_name","last_name"};
		User user = facebook.fetchObject("me", User.class, fields);
		String name=user.getId();
		String birthday=user.getBirthday();

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(name, null, null);
		SecurityContextHolder.getContext().setAuthentication(authentication);


	}

	public void logout(HttpServletRequest request) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		System.out.println(name);

		System.out.println("llego al logout");
		HttpSession session = request.getSession(false);
		session.invalidate();
	}
}

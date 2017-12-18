package com.yolotengo.gatewayApp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yolotengo.gatewayApp.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class LogInController {

	public static final Logger logger = LoggerFactory.getLogger(LogInController.class);

	@Autowired
	UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(@RequestBody String fbToken) {
		userService.isUserExists(fbToken);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public void logout(HttpServletRequest request) {
		userService.logout(request);
	}


}
package com.yolotengo.gatewayApp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yolotengo.gatewayApp.service.MutantService;
import com.yolotengo.gatewayApp.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	MutantService mutantService;

	@RequestMapping(value = "/mutant/", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> isMutant(@RequestBody DnaDTO dna) {
		try {
			if (mutantService.isMutant(dna.getDna())) {
				return ResponseEntity.ok(HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CustomErrorType("No Es mutante"));
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(new CustomErrorType("fallo al analizar ADN"));
		}

	}

	@RequestMapping(value = "/getallmutants/", method = RequestMethod.GET)
	public ResponseEntity<?> getAllMutants() {
		try {
			return ResponseEntity.ok(mutantService.getAllMutants());
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(new CustomErrorType("fallo al obtener mutantes"));
		}

	}

	@RequestMapping(value = "/stats", method = RequestMethod.GET)
	public ResponseEntity<?> getStats() {
		try {
			return ResponseEntity.ok(mutantService.getStats());
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(new CustomErrorType("fallo al obtener mutantes"));
		}

	}

	@RequestMapping(value = "/restart", method = RequestMethod.POST)
	public void restart() {
		mutantService.restart();
	}

}
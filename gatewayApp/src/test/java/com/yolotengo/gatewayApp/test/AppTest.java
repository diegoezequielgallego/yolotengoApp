package com.yolotengo.gatewayApp.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yolotengo.gatewayApp.SpringBootCRUDApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.yolotengo.gatewayApp.configuration.JpaConfiguration;
import com.yolotengo.gatewayApp.dto.CountDTO;
import com.yolotengo.gatewayApp.repositories.MutantRepository;
import com.yolotengo.gatewayApp.service.MutantService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ SpringBootCRUDApp.class, JpaConfiguration.class })
public class AppTest {
	
	@Autowired
	MutantService mutantService;

	@Autowired
	MutantRepository mutantRepository;


	@Test
	public void testIsMutant() throws Exception {
		mutantRepository.deleteAll();
		mutantService.clearStats();
		
		List<String> aux;
		//mutante
		aux = Arrays.asList("ATGGGG","CATTGA","TTATGA","TGAAGA","CCCCTA","TCACTG");
		Assert.isTrue(mutantService.isMutant(aux));
		
		//humano
		aux = Arrays.asList("ATGG","CATT","TTAT","TGAA");
		Assert.isTrue(!mutantService.isMutant(aux));
		
		//mutante
		aux = Arrays.asList("ATGGACT","CATTAAA","TTATACA","TGCAACT","TTACAAT","ATGGCCT","AACTACT");
		Assert.isTrue(mutantService.isMutant(aux));
		
		//mutante
		aux = Arrays.asList("ATGGGG","CATTAA","TTAAAC","TGCAAC","TTACAA");
		Assert.isTrue(mutantService.isMutant(aux));
		
		//dejo un sleep de 3 segundos asi le doy tiempo a que guarde en los thread paralelos 
		Thread.sleep(3000);
		
		CountDTO count = mutantService.getStats();
		Assert.isTrue(count.getCountHumanDna().equals(1L));
		Assert.isTrue(count.getCountMutantDna().equals(3L));
	}
	
	@Test
	public void bulkTest() throws Exception {
		mutantRepository.deleteAll();
		mutantService.clearStats();
		
		for (int i = 0; i < 1000; i++) {
			mutantService.isMutant(generateRandomDna());
		}
		//dejo un sleep de 10 segundos asi le doy tiempo a que guarde en los thread paralelos 
		Thread.sleep(5000);
		
		CountDTO count = mutantService.getStats();
		Long total = count.getCountHumanDna() + count.getCountMutantDna();
		long auxTotal = mutantRepository.count();
		
		Assert.isTrue(total.equals(auxTotal));
	}
	
	private List<String> generateRandomDna(){
		List<String> dna = new ArrayList<>();
		for (int i = 0; i <= 4; i++) {
			dna.add(generateRandomNitro());
		}
		return dna;
	}
	
	private String generateRandomNitro(){
		List<String> nitro = Arrays.asList("A","T","C","G");
		String aux = nitro.get(0 + (int)(Math.random() * 4))
				+nitro.get(0 + (int)(Math.random() * 4))
				+nitro.get(0 + (int)(Math.random() * 4))
				+nitro.get(0 + (int)(Math.random() * 4))
				+nitro.get(0 + (int)(Math.random() * 4));
		return aux;
		
	}

}

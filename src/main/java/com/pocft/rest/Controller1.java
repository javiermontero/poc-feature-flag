
package com.pocft.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pocft.FeatureFlag;
import com.pocft.domain.Feature;

@RestController
@RequestMapping("/poc")
public class Controller1 {
	
	@GetMapping()
	@FeatureFlag({Feature.ONE,Feature.THREE})
	public String featureUnoTres() {
		return Feature.ONE.getfWord() + Feature.THREE.getfWord();
		
	}
	
	@GetMapping()
	public String sayHelddddd2() {
		return "NO FEATURES";
		
	}

}

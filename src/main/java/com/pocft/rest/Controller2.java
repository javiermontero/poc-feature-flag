package com.pocft.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pocft.FeatureFlag;
import com.pocft.domain.Feature;

@RestController
@RequestMapping("/poc")
public class Controller2 {
	
	@GetMapping
	@FeatureFlag(Feature.TWO)
	public String featureDos() {
		return Feature.TWO.getfWord();
		
	}

}

package com.pocft;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.pocft.rest.handler.FeatureFlagRequestMappingHandlerMapping;

@Configuration
@EnableWebMvc
public class ConfigMvc {

	
	@Bean
    public WebMvcRegistrations requestMappingHandlerMapping() {
        return new WebMvcRegistrations() {
        	
        	 public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        			return new FeatureFlagRequestMappingHandlerMapping();
        		}
		};
    }
}

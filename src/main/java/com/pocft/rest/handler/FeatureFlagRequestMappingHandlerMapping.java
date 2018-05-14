package com.pocft.rest.handler;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.pocft.FeatureFlag;
import com.pocft.PftConstants;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FeatureFlagRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
	
	@Override
	protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
		final FeatureFlag typeAnnotation = AnnotationUtils.findAnnotation(handlerType, FeatureFlag.class);
		return createCondition(typeAnnotation);
	}
	
	@Nullable
	protected  RequestMappingInfo getMatchingMapping(RequestMappingInfo mapping, HttpServletRequest request) {
		final RequestCondition<?> matchingCondition = (RequestCondition<?>) mapping.getCustomCondition();
		
		
		if (matchingCondition==null) {
		//if call to mapping without @FeatureFlag with a FeatureFlag in the Header --> not mapping.
			if (request.getHeader(PftConstants.FT_HEADER)==null) {
				return mapping;
			}else return null;
		
		} else 	if  (matchingCondition.getMatchingCondition(request)!=null) {
			return mapping;
		}
		return null;
	}

	@Override
	protected RequestCondition<?> getCustomMethodCondition(Method method) {
		final FeatureFlag methodAnnotation = AnnotationUtils.findAnnotation(method, FeatureFlag.class);
		return createCondition(methodAnnotation);
	}

	private RequestCondition<?> createCondition(FeatureFlag accessMapping) {
		
		return (accessMapping == null) ?  null: new FeatureFlagRequestCondition(Arrays.asList(accessMapping.value()));
				
	}


}

package com.pocft.rest.handler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

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
	protected RequestCondition<FeatureFlagRequestCondition> getCustomTypeCondition(Class<?> handlerType) {
		final FeatureFlag typeAnnotation = AnnotationUtils.findAnnotation(handlerType, FeatureFlag.class);
		return createCondition(typeAnnotation);
	}
	
	@Nullable
	protected  RequestMappingInfo getMatchingMapping(RequestMappingInfo mapping, HttpServletRequest request) {
		final Optional<RequestCondition<FeatureFlagRequestCondition>> nullableMatchingCondition = Optional.ofNullable((RequestCondition<FeatureFlagRequestCondition>) mapping.getCustomCondition());
		return !nullableMatchingCondition.isPresent()? handlerNotFoundMatchingCondition(mapping, request):handlerFoundMatchingCondition(mapping, request, nullableMatchingCondition.get());
	}

	@Override
	protected RequestCondition<FeatureFlagRequestCondition> getCustomMethodCondition(Method method) {
		final FeatureFlag methodAnnotation = AnnotationUtils.findAnnotation(method, FeatureFlag.class);
		return createCondition(methodAnnotation);
	}

	private RequestCondition<FeatureFlagRequestCondition> createCondition(FeatureFlag accessMapping) {
		return (accessMapping == null) ?  null: new FeatureFlagRequestCondition(Arrays.asList(accessMapping.value()));
	}

	private RequestMappingInfo handlerNotFoundMatchingCondition(RequestMappingInfo mapping, HttpServletRequest request) {
		return request.getHeader(PftConstants.FT_HEADER)==null? mapping:null;
	}

	private RequestMappingInfo handlerFoundMatchingCondition(RequestMappingInfo mapping, HttpServletRequest request, RequestCondition<FeatureFlagRequestCondition> mCondition) {
		return mCondition.getMatchingCondition(request) != null? mapping:null;
	}

}

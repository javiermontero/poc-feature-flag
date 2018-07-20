package com.pocft.rest.handler;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import com.pocft.PftConstants;
import com.pocft.domain.Feature;

public class FeatureFlagRequestCondition implements RequestCondition<FeatureFlagRequestCondition> {

	private final Set<String> featureFlags;

	public FeatureFlagRequestCondition() {
		this.featureFlags = Collections.unmodifiableSet(Collections.emptySet());
	}

	public FeatureFlagRequestCondition(Collection<Feature> features) {

		this.featureFlags = Optional.ofNullable(features)
				.map(c -> c.stream().map(Feature::getValue))
				   .orElse(Stream.empty())
				   .collect(Collectors.toCollection(HashSet::new));

	}

	private FeatureFlagRequestCondition(Set<String> features) {

		this.featureFlags = Collections.unmodifiableSet(new HashSet<>(Optional.ofNullable(features).orElse(Collections.emptySet())));
	}

	@Override
	public FeatureFlagRequestCondition getMatchingCondition(HttpServletRequest request) {

		FeatureFlagRequestCondition matchingConditionResult = null;
		final String actualFeatureFlag = request.getHeader(PftConstants.FT_HEADER);
		if (this.featureFlags.contains(actualFeatureFlag)) {
			matchingConditionResult = this;
		}
		return matchingConditionResult;
	}

	@Override
	public int compareTo(FeatureFlagRequestCondition other, HttpServletRequest request) {

		int isEqual;
		final Optional<FeatureFlagRequestCondition> otherNullable = Optional.ofNullable(other);
		Set<String> falgsToMatch = otherNullable.orElse(new FeatureFlagRequestCondition()).featureFlags;
		final String actualFeatureFlag = Optional.ofNullable(request.getHeader(PftConstants.FT_HEADER)).orElse(PftConstants.EMPTY);
		switch (actualFeatureFlag) {
			case PftConstants.EMPTY:
				isEqual = falgsToMatch.isEmpty()? -1 : 1;
				break;
		    default:
				isEqual = this.featureFlags.stream().anyMatch(item -> falgsToMatch.contains(item ))? 0 : featureFlags.contains(actualFeatureFlag)? -1 : 1;
		}

		return isEqual;
	}


	@Override
	public FeatureFlagRequestCondition combine(FeatureFlagRequestCondition other) {

		final Optional<FeatureFlagRequestCondition> otherNullable = Optional.ofNullable(other);
		Set<String> allFeatureFlags = new LinkedHashSet<String>(this.featureFlags);
		allFeatureFlags.addAll(otherNullable.orElse(new FeatureFlagRequestCondition()).featureFlags);

		return new FeatureFlagRequestCondition(allFeatureFlags);
	}

}

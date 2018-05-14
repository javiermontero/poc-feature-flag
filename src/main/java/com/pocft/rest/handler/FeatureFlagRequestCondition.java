package com.pocft.rest.handler;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import com.pocft.PftConstants;
import com.pocft.domain.Feature;

public class FeatureFlagRequestCondition implements RequestCondition<FeatureFlagRequestCondition> {

	private final Set<String> featureFlags;

	public FeatureFlagRequestCondition(Collection<Feature> features) {
		if (features != null) {
			List<String> featuresLikeString = features.stream().map(Feature::getfWord).collect(Collectors.toList());
			this.featureFlags = Collections.unmodifiableSet(new HashSet<>(featuresLikeString));
		}else {
			this.featureFlags = Collections.emptySet();
		}
	}

	private FeatureFlagRequestCondition(Set<String> features) {
		if (features != null) {
			this.featureFlags = Collections.unmodifiableSet(new HashSet<>(features));
		}else {
			this.featureFlags = Collections.emptySet();
		}
	}

	@Override
	public FeatureFlagRequestCondition getMatchingCondition(HttpServletRequest request) {

		final String actualFeatureFlag = request.getHeader(PftConstants.FT_HEADER);
		if (this.featureFlags.contains(actualFeatureFlag)) {
			return this;
		}
		return null;
	}

	@Override
	public int compareTo(FeatureFlagRequestCondition other, HttpServletRequest request) {
		final String actualFeatureFlag = request.getHeader(PftConstants.FT_HEADER);
		if (actualFeatureFlag == null) {
			if (other.featureFlags.size() > 0) {
				return -1;
			} else
				return 1;

		}
		if (featureFlags.contains(actualFeatureFlag)) {
			return -1;
		} else
			return 1;
	}


	@Override
	public FeatureFlagRequestCondition combine(FeatureFlagRequestCondition other) {
		Set<String> allFeatureFlags = new LinkedHashSet<String>(this.featureFlags);
		allFeatureFlags.addAll(other.featureFlags);
		return new FeatureFlagRequestCondition(allFeatureFlags);
	}

}

	Set<AlternateHandle.Id> skipMe(Set<AlternateHandle.Id> skips) {
		Set<AlternateHandle.Id> withMe = new HashSet<AlternateHandle.Id>();
		if (skips != null) {
			withMe.addAll(skips);
		}
		withMe.add(getAlternateId());
		return withMe;
	}


	private Set<AlternateHandle> skipMe(Set<AlternateHandle> skips) {
		Set<AlternateHandle> withMe = new HashSet<AlternateHandle>();
		if (skips != null)
			withMe.addAll(skips);
		withMe.add(new AlternateRepository(this));
		return withMe;
	}


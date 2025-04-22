	Set<AlternateHandle> skipMe(Set<AlternateHandle> skips) {
		Set<AlternateHandle> withMe = new HashSet<AlternateHandle>();
		if (skips != null)
			withMe.addAll(skips);
		withMe.add(new AlternateHandle(this.wrapped));
		return withMe;
	}


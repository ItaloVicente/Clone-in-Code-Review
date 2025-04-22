		return skip(alt
	}

	static AlternateHandle[] skip(AlternateHandle[] alts
			Set<AlternateHandle> skips) {
		Set<AlternateHandle> mine = new HashSet<AlternateHandle>(
				Arrays.asList(alts));
		if (skips != null)
			mine.removeAll(skips);
		return mine.toArray(new AlternateHandle[mine.size()]);
	}

	Set<AlternateHandle> addMe(Set<AlternateHandle> skips) {
		Set<AlternateHandle> withMe = new HashSet<AlternateHandle>();
		if (skips != null)
			withMe.addAll(skips);
		withMe.add(new AlternateHandle(this));
		return withMe;

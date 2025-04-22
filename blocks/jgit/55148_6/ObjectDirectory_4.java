	Set<AlternateHandle.Id> addMe(Set<AlternateHandle.Id> skips) {
		if (skips == null)
			skips = new HashSet<AlternateHandle.Id>();
		skips.add(handle.getId());
		return skips;
	}


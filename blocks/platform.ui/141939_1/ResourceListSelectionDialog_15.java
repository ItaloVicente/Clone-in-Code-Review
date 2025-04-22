		return -1;
	}

	private void gatherResources(boolean force) {
		String oldPattern = force ? null : patternString;
		patternString = adjustPattern();
		if (!force && patternString.equals(oldPattern)) {

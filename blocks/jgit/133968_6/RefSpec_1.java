	public boolean destinationContains(String refPattern) {
		checkValid(refPattern);
		return destinationContains(thisDst
	}

	public boolean destinationContains(RefSpec r) {
		return destinationContains(r.getDestination());
	}


	public boolean destinationContains(String ref) {
		checkValid(ref);
		return destinationContains(thisDst
	}

	public boolean destinationContains(RefSpec r) {
		return destinationContains(r.getDestination());
	}


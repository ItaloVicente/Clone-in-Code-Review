	public boolean destinationContains(String refPattern) {
		checkValid(refPattern);
		return destinationIntersects(getDestination()
	}

	public boolean destinationContains(RefSpec ref) {
		return destinationIntersects(getDestination()
	}


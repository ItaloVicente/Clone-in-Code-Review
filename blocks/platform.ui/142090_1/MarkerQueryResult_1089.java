	public MarkerQueryResult(String[] markerAttributeValues) {
		if (markerAttributeValues == null) {
			throw new IllegalArgumentException();
		}
		values = markerAttributeValues;
		computeHashCode();
	}

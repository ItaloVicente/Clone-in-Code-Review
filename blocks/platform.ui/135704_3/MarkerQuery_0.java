		this(markerType, markerAttributes, false);
	}

	public MarkerQuery(String markerType, String[] markerAttributes, boolean matchTypeChildren) {
		if (markerAttributes == null) {
			throw new IllegalArgumentException();
		}

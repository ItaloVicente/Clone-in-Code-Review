	private String type;

	private String[] attributes;

	private boolean matchTypeChildren;

	private int hashCode;

	public MarkerQuery(String markerType, String[] markerAttributes) {
		this(markerType, markerAttributes, false);
	}

	public MarkerQuery(String markerType, String[] markerAttributes, boolean matchTypeChildren) {
		if (markerAttributes == null) {
			throw new IllegalArgumentException();
		}

		type = markerType;
		attributes = markerAttributes;
		this.matchTypeChildren = matchTypeChildren;
		computeHashCode();
	}

	public MarkerQueryResult performQuery(IMarker marker) {
		try {
			if (type != null) {
				if (matchTypeChildren) {
					if(!marker.isSubtypeOf(type)) {
						return null;
					}
				} else {
					if(!type.equals(marker.getType())) {
						return null;
					}
				}

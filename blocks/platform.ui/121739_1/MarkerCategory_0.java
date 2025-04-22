		MarkerEntry[] myChildren = children;
		if (myChildren != null) {
			return myChildren;
		}
		MarkerItem[] allMarkers = markers.getMarkerEntryArray();
		int markersLength = allMarkers.length;
		if (start >= markersLength || end >= markersLength) {
			return new MarkerEntry[0];

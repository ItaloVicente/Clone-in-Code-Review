		if (children == null) {
			MarkerItem[] allMarkers = markers.getMarkerEntryArray();
			int totalSize = getChildrenCount();
			children = new MarkerEntry[totalSize];
			System.arraycopy(allMarkers, start, children, 0, totalSize);
			for (MarkerEntry markerEntry : children) {
				markerEntry.setCategory(this);
			}

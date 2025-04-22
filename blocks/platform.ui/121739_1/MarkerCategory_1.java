		int totalSize = getChildrenCount();
		myChildren = new MarkerEntry[totalSize];
		System.arraycopy(allMarkers, start, myChildren, 0, totalSize);
		for (MarkerEntry markerEntry : myChildren) {
			markerEntry.setCategory(this);
		}
		children = myChildren;
		return myChildren;
	}

	void resetChildren() {
		children = null;

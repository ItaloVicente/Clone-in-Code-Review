		marker = toCopy;
		refresh();
	}

	public void clearCache() {
		resourceNameKey = null;
		descriptionKey = null;
	}

	public void refresh() {
		clearCache();

		description = Util.getProperty(IMarker.MESSAGE, marker);
		resourceName = Util.getResourceName(marker);
		inFolder = Util.getContainerName(marker);
		shortFolder = null;
		line = marker.getAttribute(IMarker.LINE_NUMBER, -1);
		locationString = marker.getAttribute(IMarker.LOCATION,

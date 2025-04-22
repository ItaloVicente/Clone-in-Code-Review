	private MarkerUtil() {
	}

	static int getCharEnd(IMarker marker) {
		return marker.getAttribute(IMarker.CHAR_END, -1);
	}

	static int getCharStart(IMarker marker) {
		return marker.getAttribute(IMarker.CHAR_START, -1);
	}

	static String getContainerName(IMarker marker) {
		IPath path = marker.getResource().getFullPath();
		int n = path.segmentCount() - 1; // n is the number of segments in container, not path
		if (n <= 0) {

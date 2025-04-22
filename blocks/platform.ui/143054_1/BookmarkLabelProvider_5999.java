		return null;
	}

	public static String getContainerName(IMarker marker) {
		IPath path = marker.getResource().getFullPath();
		int n = path.segmentCount() - 1;
		if (n <= 0) {

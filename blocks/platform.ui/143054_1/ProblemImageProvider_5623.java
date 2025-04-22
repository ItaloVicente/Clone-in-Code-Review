	private boolean isMarkerType(IMarker marker, String type) {
		try {
			return marker.isSubtypeOf(type);
		} catch (CoreException e) {
			return false;
		}
	}

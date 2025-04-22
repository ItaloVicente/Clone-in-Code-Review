		if (result.isEmpty()) {
			return MarkerSupportInternalUtilities.EMPTY_MARKER_ARRAY;
		}
		IMarker[] markers = new IMarker[result.size()];
		result.toArray(markers);
		return markers;

		return showMarkers(page, new IMarker[] { marker }, showView);
	}

	public static boolean showMarkers(IWorkbenchPage page, IMarker[] markers, boolean showView) {

		if (null == markers || 0 == markers.length || null == markers[0])
			return false;

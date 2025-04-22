	private static IMarker[] getMarkersOfView(String viewId, IMarker[] markers)
			throws CoreException {
		if (null == viewId) // all markers should be shown
			return markers;

		ArrayList<IMarker> markersOfView = new ArrayList<>();
		for (IMarker marker : markers) {
			if (viewId == getViewId(marker))
				markersOfView.add(marker);
		}
		return markersOfView.toArray(new IMarker[markersOfView.size()]);
	}


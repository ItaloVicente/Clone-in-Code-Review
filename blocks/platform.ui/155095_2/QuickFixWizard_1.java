		Optional<IMarkerResolution> resolution = quickFixPage.getSelectedMarkerResolution();
		if (!resolution.isPresent()) {
			return true; // for backward compatibility
		}
		final IMarker[] markers = quickFixPage.getCheckedMarkers();
		if (markers.length == 0) {
			return true; // for backward compatibility
		}
		IRunnableWithProgress finishRunnable = monitor -> processResolution(resolution.get(), markers, monitor);

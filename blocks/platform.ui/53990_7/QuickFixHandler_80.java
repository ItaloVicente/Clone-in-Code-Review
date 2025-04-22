		IRunnableWithProgress resolutionsRunnable = monitor -> {
			monitor.beginTask(MarkerMessages.resolveMarkerAction_computationManyAction, 100);

			IMarker[] allMarkers = view.getAllMarkers();
			monitor.worked(20);
			IMarkerResolution[] resolutions = IDE.getMarkerHelpRegistry().getResolutions(firstSelectedMarker);
			int progressCount = 80;
			if (resolutions.length > 1) {
				progressCount= progressCount / resolutions.length;
			}
			for (int i = 0; i < resolutions.length; i++) {
				IMarkerResolution markerResolution= resolutions[i];
				if (markerResolution instanceof WorkbenchMarkerResolution) {
					IMarker[] other = ((WorkbenchMarkerResolution)markerResolution).findOtherMarkers(allMarkers);
					if (containsAllButFirst(other, selectedMarkers)) {
						Collection<IMarker> markers1 = new LinkedHashSet<>(other.length + 1);
						markers1.add(firstSelectedMarker);
						markers1.addAll(Arrays.asList(other));
						resolutionsMap.put(markerResolution, markers1);

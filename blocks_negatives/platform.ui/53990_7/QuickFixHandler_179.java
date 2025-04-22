		IRunnableWithProgress resolutionsRunnable = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) {
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
							Collection<IMarker> markers = new LinkedHashSet<>(other.length + 1);
							markers.add(firstSelectedMarker);
							markers.addAll(Arrays.asList(other));
							resolutionsMap.put(markerResolution, markers);
						}
					} else if (selectedMarkers.length == 1) {
						Collection<IMarker> markers = new ArrayList<>(1);
						markers.add(firstSelectedMarker);
						resolutionsMap.put(markerResolution, markers);

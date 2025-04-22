				if (partRef.getPart(false) != ExtendedMarkersView.this) {
					return;
				}
				isViewVisible = false;
				Markers markers = getActiveViewerInputClone();
				Integer[] counts = markers.getMarkerCounts();
				setTitleToolTip(getStatusMessage(markers, counts));

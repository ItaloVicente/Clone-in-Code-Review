					if (severitiesPerContainer != null) {
						IMarkerDelta[] markerDeltas = delta.getMarkerDeltas();
						for (IMarkerDelta markerDelta : markerDeltas) {
							if (markerManager.isSubtype(markerDelta.getType(), IMarker.PROBLEM)) {
								severitiesPerContainer.cancel(true);
								severitiesPerContainer = null;

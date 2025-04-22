					IMarkerDelta[] markerDeltas = delta.getMarkerDeltas();
					for (IMarkerDelta markerDelta : markerDeltas) {
						if (markerManager.isSubtype(markerDelta.getType(), IMarker.PROBLEM)) {
							if (severities != null && !severities.isDone()) {
								severities.cancel(true);
								severities = null;

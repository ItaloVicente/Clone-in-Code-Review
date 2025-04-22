            getTaskList().getWorkspace().run(monitor -> {
			    for (int i = 0; i < markerData.length; i++) {
			        if (!markerData[i].getType().equals(IMarker.TASK)) {
						continue;
					}
			        newMarkerResources.add(markerData[i].getResource());
			        newMarkerAttributes.add(markerData[i].getAttributes());
			    }
			}, null);

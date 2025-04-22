			PlatformUI.getWorkbench().getProgressService().run(true, true, monitor -> {
				monitor.beginTask(MarkerMessages.markCompletedHandler_task, 100);
				IMarker[] markers = getSelectedMarkers(finalEvent);
				if (markers.length == 0) {
					return;
				}

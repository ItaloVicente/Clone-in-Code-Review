	private void processResolution(final IMarkerResolution resolution, final IMarker[] markers,
			IProgressMonitor monitor) {
		monitor.beginTask(MarkerMessages.MarkerResolutionDialog_Fixing, markers.length);
		final Display display = getShell().getDisplay();
		display.readAndDispatch();
		if (resolution instanceof WorkbenchMarkerResolution) {
			((WorkbenchMarkerResolution) resolution).run(markers, monitor);
		} else {
			Translation translation = new Translation();
			for (IMarker marker : markers) {
				display.readAndDispatch();
				if (monitor.isCanceled()) {
					return;
				}
				monitor.subTask(translation.message(marker).orElse("")); //$NON-NLS-1$
				resolution.run(marker);
				monitor.worked(1);
			}
		}
	}


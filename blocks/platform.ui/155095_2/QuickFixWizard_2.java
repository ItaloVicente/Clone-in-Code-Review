	private void processResolution(final IMarkerResolution resolution, final IMarker[] markers,
			IProgressMonitor monitor) {
		monitor.beginTask(MarkerMessages.MarkerResolutionDialog_Fixing, markers.length);
		ensureRepaint();
		if (resolution instanceof WorkbenchMarkerResolution) {
			((WorkbenchMarkerResolution) resolution).run(markers, monitor);
		} else {
			Translation translation = new Translation();
			for (IMarker marker : markers) {
				ensureRepaint();
				if (monitor.isCanceled()) {
					return;
				}
				monitor.subTask(translation.message(marker).orElse("")); //$NON-NLS-1$
				resolution.run(marker);
				monitor.worked(1);
			}
		}
	}

	private void ensureRepaint() {
		final Display display = getShell().getDisplay();
		boolean dispatch = true;
		while (dispatch) {
			dispatch = display.readAndDispatch();
		}
	}


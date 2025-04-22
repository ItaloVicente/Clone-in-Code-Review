	public MarkerResolutionSelectionDialog(Shell shell, IMarkerResolution[] markerResolutions) {
		super(shell);
		if (markerResolutions == null || markerResolutions.length == 0) {
			throw new IllegalArgumentException();
		}
		resolutions = markerResolutions;
		setTitle(IDEWorkbenchMessages.MarkerResolutionSelectionDialog_title);
		setMessage(IDEWorkbenchMessages.MarkerResolutionSelectionDialog_messageLabel);

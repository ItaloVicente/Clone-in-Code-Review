    /**
     * Creates an instance of this dialog to display
     * the given resolutions.
     * <p>
     * There must be at least one resolution.
     * </p>
     *
     * @param shell the parent shell
     * @param markerResolutions the resolutions to display
     */
    public MarkerResolutionSelectionDialog(Shell shell,
            IMarkerResolution[] markerResolutions) {
        super(shell);
        if (markerResolutions == null || markerResolutions.length == 0) {
            throw new IllegalArgumentException();
        }
        resolutions = markerResolutions;
        setTitle(IDEWorkbenchMessages.MarkerResolutionSelectionDialog_title);
        setMessage(IDEWorkbenchMessages.MarkerResolutionSelectionDialog_messageLabel);

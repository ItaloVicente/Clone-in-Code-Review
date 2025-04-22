    /**
     * Create a modal dialog on the arugment shell, using and updating the
     * argument data object.
     * @param parentShell the parent shell for this dialog
     * @param launchData the launch data from past launches
     *
     * @param suppressAskAgain
     *            true means the dialog will not have a "don't ask again" button
     * @param centerOnMonitor indicates whether the dialog should be centered on
     * the monitor or according to it's parent if there is one
     */
    public ChooseWorkspaceDialog(Shell parentShell,
            ChooseWorkspaceData launchData, boolean suppressAskAgain, boolean centerOnMonitor) {
        super(parentShell);
        this.launchData = launchData;
        this.suppressAskAgain = suppressAskAgain;
        this.centerOnMonitor = centerOnMonitor;
    }

    /**
     * Show the dialog to the user (if needed). When this method finishes,
     * #getSelection will return the workspace that should be used (whether it
     * was just selected by the user or some previous default has been used.
     * The parameter can be used to override the users preference.  For example,
     * this is important in cases where the default selection is already in use
     * and the user is forced to choose a different one.
     *
     * @param force
     *            true if the dialog should be opened regardless of the value of
     *            the show dialog checkbox
     */
    public void prompt(boolean force) {
        if (force || launchData.getShowDialog()) {
            open();

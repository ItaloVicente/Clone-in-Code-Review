    /**
     * Returns the display for use by the receiver when running in an
     * asyncExec. If it is not set then the display set in the workbench
     * is used.
     * If the display is null the job will not be run.
     *
     * @return Display or <code>null</code>.
     */
    public Display getDisplay() {
        if (cachedDisplay == null && PlatformUI.isWorkbenchRunning()) {

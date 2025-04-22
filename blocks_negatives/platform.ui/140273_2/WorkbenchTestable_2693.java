    /**
     * Initializes the workbench testable with the display and workbench,
     * and notifies all listeners that the tests can be run.
     *
     * @param display the display
     * @param workbench the workbench
     */
    public void init(Display display, IWorkbench workbench) {
        Assert.isNotNull(display);
        Assert.isNotNull(workbench);
        this.display = display;
        this.workbench = workbench;
        if (getTestHarness() != null) {
            Runnable runnable = () -> {

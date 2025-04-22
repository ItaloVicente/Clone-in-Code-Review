        return !sourceDeleted && super.isSaveOnCloseNeeded();
    }

    /**
     * Posts the update code "behind" the running operation.
     *
     * @param runnable the update code
     */
    private void update(Runnable runnable) {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
        if (windows != null && windows.length > 0) {
            Display display = windows[0].getShell().getDisplay();
            display.asyncExec(runnable);
        } else
            runnable.run();
    }

    private boolean isDirty = false;
    private void updateDirtyFlag() {
    	final Runnable dirtyFlagUpdater = new Runnable() {

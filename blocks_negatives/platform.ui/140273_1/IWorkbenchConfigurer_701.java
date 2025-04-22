    /**
     * Returns the workbench window manager.
     *
     * @return the workbench window manager
     *
     * Note:IWorkbenchWindow is implemented using JFace's Window (and therefore uses WindowManager),
     *   but this is an implementation detail
     */
    WindowManager getWorkbenchWindowManager();

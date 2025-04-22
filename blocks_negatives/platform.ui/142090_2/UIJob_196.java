        return Job.ASYNC_FINISH;
    }

    /**
     * Run the job in the UI Thread.
     *
     * @param monitor
     * @return IStatus
     */
    public abstract IStatus runInUIThread(IProgressMonitor monitor);

    /**
     * Sets the display to execute the asyncExec in. Generally this is not'
     * used if there is a valid display available via PlatformUI.isWorkbenchRunning().
     *
     * @param runDisplay
     *            Display
     * @see UIJob#getDisplay()
     * @see PlatformUI#isWorkbenchRunning()
     */
    public void setDisplay(Display runDisplay) {
        Assert.isNotNull(runDisplay);
        cachedDisplay = runDisplay;
    }

    /**
     * Returns the display for use by the receiver when running in an
     * asyncExec. If it is not set then the display set in the workbench
     * is used.
     * If the display is null the job will not be run.
     *
     * @return Display or <code>null</code>.
     */
    public Display getDisplay() {
        if (cachedDisplay == null) {
            cachedDisplay = Services.getInstance().getDisplay();
        }
        if (cachedDisplay == null) {
            cachedDisplay = Display.getCurrent();
        }
        if (cachedDisplay == null) {
            cachedDisplay = Display.getDefault();
        }
        return cachedDisplay;
    }

    public static IStatus getStatus(Throwable t) {
        String message = StatusUtil.getLocalizedMessage(t);

        return newError(message, t);
    }

    public static IStatus newError(String message, Throwable t) {
        String pluginId = IProgressConstants.PLUGIN_ID;
        int errorCode = IStatus.OK;

        if (t instanceof CoreException) {
            CoreException ce = (CoreException) t;
            pluginId = ce.getStatus().getPlugin();
            errorCode = ce.getStatus().getCode();
        }

        return new Status(IStatus.ERROR, pluginId, errorCode, message,
                StatusUtil.getCause(t));
    }

    protected UISynchronize getUiSynchronize() {
        return Services.getInstance().getUISynchronize();
    }

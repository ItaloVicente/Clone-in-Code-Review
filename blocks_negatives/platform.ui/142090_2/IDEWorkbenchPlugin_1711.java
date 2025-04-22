    }

    /* Return the default instance of the receiver. This represents the runtime plugin.
     *
     * @see AbstractPlugin for the typical implementation pattern for plugin classes.
     */
    public static IDEWorkbenchPlugin getDefault() {
        return inst;
    }

    /**
     * Return the workspace used by the workbench
     *
     * This method is internal to the workbench and must not be called
     * by any plugins.
     */
    public static IWorkspace getPluginWorkspace() {
        return ResourcesPlugin.getWorkspace();
    }

    /**
     * Logs the given message to the platform log.
     *
     * If you have an exception in hand, call log(String, Throwable) instead.
     *
     * If you have a status object in hand call log(String, IStatus) instead.
     *
     * This convenience method is for internal use by the IDE Workbench only and
     * must not be called outside the IDE Workbench.
     *
     * @param message
     *            A high level UI message describing when the problem happened.
     */
    public static void log(String message) {
        getDefault().getLog().log(
                StatusUtil.newStatus(IStatus.ERROR, message, null));
    }

    /**
     * Logs the given message and throwable to the platform log.
     *
     * If you have a status object in hand call log(String, IStatus) instead.
     *
     * This convenience method is for internal use by the IDE Workbench only and
     * must not be called outside the IDE Workbench.
     *
     * @param message
     *            A high level UI message describing when the problem happened.
     * @param t
     *            The throwable from where the problem actually occurred.
     */
    public static void log(String message, Throwable t) {
        IStatus status = StatusUtil.newStatus(IStatus.ERROR, message, t);
        log(message, status);
    }

    /**
     * Logs the given throwable to the platform log, indicating the class and
     * method from where it is being logged (this is not necessarily where it
     * occurred).
     *
     * This convenience method is for internal use by the IDE Workbench only and
     * must not be called outside the IDE Workbench.
     *
     * @param clazz
     *            The calling class.
     * @param methodName
     *            The calling method name.
     * @param t
     *            The throwable from where the problem actually occurred.
     */

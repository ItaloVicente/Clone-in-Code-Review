    }

    /**
     * Logs the given message to the platform log.
     *
     * If you have an exception in hand, call log(String, Throwable) instead.
     *
     * If you have a status object in hand call log(String, IStatus) instead.
     *
     * This convenience method is for internal use by the Workbench only and
     * must not be called outside the Workbench.
     *
     * @param message
     *            A high level UI message describing when the problem happened.
     */
    public static void log(String message) {
        getDefault().getLog().log(
                StatusUtil.newStatus(IStatus.ERROR, message, null));
    }

    /**
     * Log the throwable.
     * @param t
     */
    public static void log(Throwable t) {

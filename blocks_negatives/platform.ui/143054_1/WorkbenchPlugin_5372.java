        log(msg, t);
    }

    /**
     * Logs the given message and status to the platform log.
     *
     * This convenience method is for internal use by the Workbench only and
     * must not be called outside the Workbench.
     *
     * @param message
     *            A high level UI message describing when the problem happened.
     *            May be <code>null</code>.
     * @param status
     *            The status describing the problem. Must not be null.
     */
    public static void log(String message, IStatus status) {


    /**
     * Creates a new exception with the given message.
     *
     * @param message the message
     * @param nestedException an exception to be wrapped by this WorkbenchException
     */
    public WorkbenchException(String message, Throwable nestedException) {
        this(new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0, message,
                nestedException));
    }

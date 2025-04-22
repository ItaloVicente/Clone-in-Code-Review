    /**
     * Creates a new exception with the given message.
     *
     * @param message the message
     */
    public WorkbenchException(String message) {
        this(new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0, message, null));
    }

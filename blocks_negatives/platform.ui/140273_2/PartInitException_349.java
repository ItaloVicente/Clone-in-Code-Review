    /**
     * Creates a new exception with the given message.
     *
     * @param message the message
     * @param nestedException a exception to be wrapped by this PartInitException
     */
    public PartInitException(String message, Throwable nestedException) {
        super(message, nestedException);
    }

    /**
     * Creates a new instance of this class with the specified detail message
     * and cause.
     *
     * @param message
     *            the detail message.
     * @param cause
     *            the cause.
     */
    public ContextException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

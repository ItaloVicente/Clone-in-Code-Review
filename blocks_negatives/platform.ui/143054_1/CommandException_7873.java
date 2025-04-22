    /**
     * Creates a new instance of this class with the specified detail message
     * and cause.
     *
     * @param message
     *            the detail message; may be <code>null</code>.
     * @param cause
     *            the cause; may be <code>null</code>.
     */
    public CommandException(final String message, final Throwable cause) {
        super(message);
        this.cause = cause;
    }

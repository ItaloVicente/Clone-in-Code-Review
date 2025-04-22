    /**
     * Creates a new exception with the given status object.  The message
     * of the given status is used as the exception message.
     *
     * @param status the status object to be associated with this exception
     */
    public WorkbenchException(IStatus status) {
        super(status);
    }

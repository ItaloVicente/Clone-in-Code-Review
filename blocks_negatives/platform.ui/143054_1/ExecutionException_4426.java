    /**
     * Constructs a new instance of <code>ExecutionException</code> using an
     * instance of the new <code>ExecutionException</code>.
     *
     * @param e
     *            The exception from which this exception should be created;
     *            must not be <code>null</code>.
     * @since 3.1
     */
    public ExecutionException(final org.eclipse.core.commands.ExecutionException e) {
        super(e.getMessage(), e);
    }

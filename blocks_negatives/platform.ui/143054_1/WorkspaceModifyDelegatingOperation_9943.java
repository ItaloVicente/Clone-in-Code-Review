    /**
     * Creates a new operation which will delegate its work to the given
     * runnable. Schedule using the supplied s
     *
     * @param content
     *            the runnable to delegate to when this operation is executed
     */
    public WorkspaceModifyDelegatingOperation(IRunnableWithProgress content) {
        super();
        this.content = content;
    }

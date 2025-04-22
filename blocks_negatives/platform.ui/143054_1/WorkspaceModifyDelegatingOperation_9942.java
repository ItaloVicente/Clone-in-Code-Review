    /**
     * Creates a new operation which will delegate its work to the given
     * runnable using the provided scheduling rule.
     *
     * @param content
     *            the runnable to delegate to when this operation is executed
     * @param rule
     *            The ISchedulingRule to use or <code>null</code>.
     */
    public WorkspaceModifyDelegatingOperation(IRunnableWithProgress content,
            ISchedulingRule rule) {
        super(rule);
        this.content = content;
    }

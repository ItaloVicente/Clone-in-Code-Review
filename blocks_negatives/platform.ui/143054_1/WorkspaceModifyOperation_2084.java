    /**
     * Creates a new operation that will run using the provided
     * scheduling rule.
     * @param rule  The ISchedulingRule to use or <code>null</code>.
     * @since 3.0
     */
    protected WorkspaceModifyOperation(ISchedulingRule rule) {
        this.rule = rule;
    }

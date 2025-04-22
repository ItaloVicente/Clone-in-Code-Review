    /**
     * Returns the rule used to schedule the deferred fetching of children for this adapter.
     *
     * @param object the object whose children are being fetched
     * @return the scheduling rule. May be <code>null</code>.
     * @see org.eclipse.core.runtime.jobs.Job#setRule(ISchedulingRule)
     */
    public ISchedulingRule getRule(Object object);

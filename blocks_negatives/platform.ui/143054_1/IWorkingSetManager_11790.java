	IWorkingSet createAggregateWorkingSet(String name, String label,
			IWorkingSet[] components);

    /**
     * Re-creates and returns a working set from the state captured within the
     * given memento.
     *
     * @param memento a memento containing the state for the working set
     * @return the restored working set, or <code>null</code> if it could not be created
     *
     * @since 3.0
     */
    IWorkingSet createWorkingSet(IMemento memento);

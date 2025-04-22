    /**
     * Creates a new working set.
     * The working set is not added to the working set manager.
     *
     * @param name the name of the new working set. Should not have
     * 	leading or trailing whitespace.
     * @param elements the working set contents
     * @return a new working set with the specified name and content
     */
    IWorkingSet createWorkingSet(String name, IAdaptable[] elements);

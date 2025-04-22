    /**
     * Adds a working set to the top of the list of most recently used
     * working sets, making it the most recently used working set.
     * The last (oldest) item will be deleted if the list exceeds the
     * size limit.
     *
     * @param workingSet the working set to add to the list of most
     * 	recently used working sets.
     */
    void addRecentWorkingSet(IWorkingSet workingSet);

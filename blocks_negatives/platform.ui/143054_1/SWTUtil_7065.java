    /**
     * Returns the work queue for the given display. Creates a work queue if
     * none exists yet.
     *
     * @param d
     *            display to return queue for
     * @return a work queue (never null)
     */
    private static WorkQueue getQueueFor(final Display d) {
        WorkQueue result;
        synchronized (mapDisplayOntoWorkQueue) {

    /**
     * Return a copy of the current activity set. This copy will contain all
     * activity and category definitions that the workbench activity manager
     * contains, and will have the same enabled state. It will not have the same
     * listeners. This is useful for user interfaces that wish to modify the
     * activity enablement state (such as preference pages).
     *
     * @return a copy of the current activity set
     * @since 3.1
     */
    IMutableActivityManager createWorkingCopy();

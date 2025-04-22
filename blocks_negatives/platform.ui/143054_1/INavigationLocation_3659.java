    /**
     * Merge the receiver into <code>currentLocation</code>. Return true if
     * the two locations could be merged otherwise return false.
     * <p>
     * This message is sent to all locations before being added to the history;
     * given the change to the new location to merge itself into the current
     * location minimizing the number of entries in the navigation history.
     * </p>
     *
     * @param currentLocation where the receiver should be merged into
     * @return boolean true if the merge was possible
     */
    public boolean mergeInto(INavigationLocation currentLocation);

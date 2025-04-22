    /**
     * Creates a new instance of this class.
     *
     * @param identifier
     *            the instance of the interface that changed.
     * @param activityIdsChanged
     *            <code>true</code>, iff the activityIds property changed.
     * @param enabledChanged
     *            <code>true</code>, iff the enabled property changed.
     */
    public IdentifierEvent(IIdentifier identifier, boolean activityIdsChanged,
            boolean enabledChanged) {
        if (identifier == null) {

    /**
     * Returns whether or not this instance is enabled.  An identifier is always
     * considered enabled unless it matches only disabled activities.
     *
     * <p>
     * Notification is sent to all registered listeners if this property
     * changes.
     * </p>
     *
     * @return <code>true</code>, iff this instance is enabled.
     */
    boolean isEnabled();

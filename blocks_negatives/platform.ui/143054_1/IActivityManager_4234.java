    /**
     * Returns the set of identifiers to defined activities.
     * <p>
     * Notification is sent to all registered listeners if this property
     * changes.
     * </p>
     *
     * @return the set of identifiers to defined activities. This set may be
     *         empty, but is guaranteed not to be <code>null</code>. If this
     *         set is not empty, it is guaranteed to only contain instances of
     *         <code>String</code>.
     */
    Set getDefinedActivityIds();

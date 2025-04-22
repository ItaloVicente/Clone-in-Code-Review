    /**
     * Returns the set of activity ids that this instance matches.  Each
     * activity in this set will have at least one pattern binding that matches
     * the string returned by {@link #getId()}.
     * <p>
     * Notification is sent to all registered listeners if this property
     * changes.
     * </p>
     *
     * @return the set of activity ids that this instance matches. This set may
     *         be empty, but is guaranteed not to be <code>null</code>. If
     *         this set is not empty, it is guaranteed to only contain
     *         instances of <code>String</code>.
     */
    Set getActivityIds();

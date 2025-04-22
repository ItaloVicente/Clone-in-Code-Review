    /**
     * Returns the set of identifiers to defined contexts.  The set is sorted by
     * the depth of the context within the tree of contexts.  So, for example,
     * a child context will always appear before its parent.
     * <p>
     * Notification is sent to all registered listeners if this property
     * changes.
     * </p>
     *
     * @return the set of identifiers to defined contexts. This set may be
     *         empty, but is guaranteed not to be <code>null</code>. If this
     *         set is not empty, it is guaranteed to only contain instances of
     *         <code>String</code>.
     */
    SortedSet getDefinedContextIds();

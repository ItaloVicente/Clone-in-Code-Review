    /**
     * Returns the set of category activity bindings for this instance.
     * <p>
     * This method will return all category activity bindings for this
     * instance, whether or not this instance is defined.
     * </p>
     * <p>
     * Notification is sent to all registered listeners if this property
     * changes.
     * </p>
     *
     * @return the set of category activity bindings. This set may be empty,
     *         but is guaranteed not to be <code>null</code>. If this set is
     *         not empty, it is guaranteed to only contain instances of <code>ICategoryActivityBinding</code>.
     * @see ICategoryActivityBinding
     */
    Set getCategoryActivityBindings();

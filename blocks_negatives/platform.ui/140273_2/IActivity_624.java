    /**
     * Returns the description of this instance suitable for display to the user.
     * <p>
     * Notification is sent to all registered listeners if this property
     * changes.
     * </p>
     *
     * @return the description of this instance. Guaranteed not to be <code>null</code>.
     * @throws NotDefinedException
     *             if this instance is not defined.
     */
    String getDescription() throws NotDefinedException;

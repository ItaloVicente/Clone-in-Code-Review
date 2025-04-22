    /**
     * <p>
     * Returns whether or not this command is handled. A command is handled if
     * it currently has an <code>IHandler</code> instance associated with it.
     * A command needs a handler to carry out the {@link ICommand#execute(Map)}
     * method.
     * </p>
     * <p>
     * Notification is sent to all registered listeners if this attribute
     * changes.
     * </p>
     *
     * @return <code>true</code>, iff this command is enabled.
     */

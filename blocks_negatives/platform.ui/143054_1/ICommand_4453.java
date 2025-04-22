    /**
     * <p>
     * Returns the list of key sequence bindings for this handle. This method
     * will return all key sequence bindings for this handle's identifier,
     * whether or not the command represented by this handle is defined.
     * </p>
     * <p>
     * Notification is sent to all registered listeners if this attribute
     * changes.
     * </p>
     *
     * @return the list of key sequence bindings. This list may be empty, but is
     *         guaranteed not to be <code>null</code>. If this list is not
     *         empty, it is guaranteed to only contain instances of
     *         <code>IKeySequenceBinding</code>.
     */
    List getKeySequenceBindings();

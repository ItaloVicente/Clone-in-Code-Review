            listener.contextChanged(event);
        }
    }

    /**
     * Returns the identifier of the parent of this instance.
     * <p>
     * Notification is sent to all registered listeners if this property
     * changes.
     * </p>
     *
     * @return the identifier of the parent of this instance. May be
     *         <code>null</code>.
     * @throws NotDefinedException
     *             if this instance is not defined.
     */
    public final String getParentId() throws NotDefinedException {
        if (!defined) {

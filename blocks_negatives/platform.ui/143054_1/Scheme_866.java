        this.parentId = parentId;

        fireSchemeChanged(new SchemeEvent(this, definedChanged, nameChanged,
                descriptionChanged, parentIdChanged));
    }

    /**
     * Notifies all listeners that this scheme has changed. This sends the given
     * event to all of the listeners, if any.
     *
     * @param event
     *            The event to send to the listeners; must not be
     *            <code>null</code>.
     */
    private final void fireSchemeChanged(final SchemeEvent event) {
        if (event == null) {
            throw new NullPointerException(
        }

        if (listeners == null) {
            return;
        }

        final Iterator listenerItr = listeners.iterator();
        while (listenerItr.hasNext()) {
            final ISchemeListener listener = (ISchemeListener) listenerItr
                    .next();
            listener.schemeChanged(event);
        }
    }

    /**
     * <p>
     * Returns the identifier of the parent of the scheme represented by this
     * handle.
     * </p>
     * <p>
     * Notification is sent to all registered listeners if this attribute
     * changes.
     * </p>
     *
     * @return the identifier of the parent of the scheme represented by this
     *         handle. May be <code>null</code>.
     * @throws NotDefinedException
     *             if the scheme represented by this handle is not defined.
     */
    public final String getParentId() throws NotDefinedException {
        if (!defined) {
            throw new NotDefinedException(
            		+ id);
        }

        return parentId;
    }

    /**
     * Unregisters an instance of <code>ISchemeListener</code> listening for
     * changes to attributes of this instance.
     *
     * @param schemeListener
     *            the instance of <code>ISchemeListener</code> to unregister.
     *            Must not be <code>null</code>. If an attempt is made to
     *            unregister an instance of <code>ISchemeListener</code> which
     *            is not already registered with this instance, no operation is
     *            performed.
     */
    public final void removeSchemeListener(final ISchemeListener schemeListener) {
        if (schemeListener == null) {
        }

        if (listeners == null) {
            return;
        }

        listeners.remove(schemeListener);

        if (listeners.isEmpty()) {
            listeners = null;
        }
    }

    /**
     * The string representation of this command -- for debugging purposes only.
     * This string should not be shown to an end user.
     *
     * @return The string representation; never <code>null</code>.
     */
    @Override

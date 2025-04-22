    }

    /**
     * Notifies all listeners that this context has changed. This sends the
     * given event to all of the listeners, if any.
     *
     * @param event
     *            The event to send to the listeners; must not be
     *            <code>null</code>.
     */
    private final void fireContextChanged(final ContextEvent event) {
        if (event == null) {

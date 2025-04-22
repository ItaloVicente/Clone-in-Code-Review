    public Object execute(final ExecutionEvent event) throws ExecutionException {
        try {
            return execute(event.getParameters());
        } catch (final org.eclipse.ui.commands.ExecutionException e) {
            throw new ExecutionException(e.getMessage(), e.getCause());
        }
    }

    /**
     * Fires an event to all registered listeners describing changes to this
     * instance.
     *
     * @param handlerEvent
     *            the event describing changes to this instance. Must not be
     *            <code>null</code>.
     */

    /**
     * Handles a help request from the underlying SWT control.
     * The default behavior is to fire a help request,
     * with the event's data modified to hold this viewer.
     * @param event the event
     *
     */
    protected void handleHelpRequest(HelpEvent event) {
        Object oldData = event.data;
        event.data = this;
        fireHelpRequested(event);
        event.data = oldData;
    }

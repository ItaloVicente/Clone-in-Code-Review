    /**
     * Updates the message (or error message) shown in the message line to
     * reflect the state of the currently active page in this container.
     * <p>
     * This method is called by the container itself
     * when its wizard page changes and may be called
     * by the page at other times to force a message
     * update.
     * </p>
     */
    public void updateMessage();

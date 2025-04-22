    /**
     * Adjusts the enable state of the Back, Next, and Finish
     * buttons to reflect the state of the currently active
     * page in this container.
     * <p>
     * This method is called by the container itself
     * when its wizard page changes and may be called
     * by the page at other times to force a button state
     * update.
     * </p>
     */
    public void updateButtons();

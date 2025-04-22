    /**
     * Closes the container from the inside.
     * @return true if the container closed successfully
     */
    boolean close();
    /**
     * Returns the action bars of the container.
     * @return action bars of the container or <code>null</code> if
     * not available.
     */
    IActionBars getActionBars();
    /**
     * Opens the url in the external browser if
     * internal browser failed to create.
     * @param url
     */
    void openInExternalBrowser(String url);

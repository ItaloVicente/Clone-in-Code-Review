    }

    /**
     * Sets the page input.
     *
     * @param input the page input
     */
    public void setPageInput(IAdaptable input) {
        pageInput = input;
    }

    /**
     * Set whether replace menu item is enabled within its parent menu.
     */
    public void setReplaceEnabled(boolean isEnabled) {
        if (replaceEnabled != isEnabled) {
            replaceEnabled = isEnabled;
            if (canRun() && parentMenuManager != null) {

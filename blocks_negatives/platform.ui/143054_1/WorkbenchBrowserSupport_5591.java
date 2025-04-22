    /**
     * For debug purposes only.
     *
     * @param desiredBrowserSupportId the desired browser system id
     */
    public void setDesiredBrowserSupportId(String desiredBrowserSupportId) {
        this.desiredBrowserSupportId = desiredBrowserSupportId;
    }

    /**
     * Dispose of the active support.
     */
    protected void dispose() {
        activeSupport = null;
        initialized = false;
    }

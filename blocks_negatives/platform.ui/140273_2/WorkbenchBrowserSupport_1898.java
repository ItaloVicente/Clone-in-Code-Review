    /**
     * Answers whether the system has a non-default browser installed.
     *
     * @return whether the system has a non-default browser installed
     */
    public boolean hasNonDefaultBrowser() {
        return !(getActiveSupport() instanceof DefaultWorkbenchBrowserSupport);
    }

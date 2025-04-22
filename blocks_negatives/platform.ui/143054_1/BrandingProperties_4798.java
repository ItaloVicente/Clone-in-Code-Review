        return tipsAndTricksHref;
    }

    /**
     * A URL for the feature's welcome page (special XML-based format) ($nl$/
     * prefix to permit locale-specific translations of entire file). Products
     * designed to run "headless" typically would not have such a page.
     */
    public URL getWelcomePageUrl() {
        if (welcomePageUrl == null) {

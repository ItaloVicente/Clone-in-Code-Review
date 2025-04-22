    /**
     * Opens the welcome page for the given feature.
     *
     * @param featureId the about info for the feature
     * @return <code>true</code> if successful, <code>false</code> otherwise
     * @throws WorkbenchException
     */
    public boolean openWelcomePage(String featureId) throws WorkbenchException {
        AboutInfo feature = findFeature(featureId);
        if (feature == null || feature.getWelcomePageURL() == null) {
            return false;
        }
        return openWelcomePage(feature);
    }

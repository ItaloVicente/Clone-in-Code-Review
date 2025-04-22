    /**
     * Saves the API preference store, if needed.
     */
    public static void saveAPIPrefs() {
        Assert.isNotNull(uiCallback);
        uiCallback.savePreferences();
    }

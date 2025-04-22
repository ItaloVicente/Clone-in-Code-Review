    /**
     * Returns the API preference store.
     *
     * @return the API preference store
     */
    public static IPreferenceStore getAPIPreferenceStore() {
        if (uiPreferenceStore == null) {
            Assert.isNotNull(uiCallback);
            uiPreferenceStore = uiCallback.getPreferenceStore();
        }
        return uiPreferenceStore;
    }

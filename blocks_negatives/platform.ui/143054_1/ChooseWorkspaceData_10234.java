    /**
     * Creates a new instance, loading persistent data if its found.
     */
    public ChooseWorkspaceData(URL instanceUrl) {
        readPersistedData();
        if (instanceUrl != null) {

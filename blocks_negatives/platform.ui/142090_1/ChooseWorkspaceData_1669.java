    /**
     * Creates a new instance, loading persistent data if its found.
     */
    public ChooseWorkspaceData(String initialDefault) {
        readPersistedData();
        setInitialDefault(initialDefault);
    }

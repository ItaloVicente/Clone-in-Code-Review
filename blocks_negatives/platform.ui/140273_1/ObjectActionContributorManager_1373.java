    /**
     * Returns the shared instance of this manager.
     * @return the shared instance of this manager
     */
    public static ObjectActionContributorManager getManager() {
        if (sharedInstance == null) {
            sharedInstance = new ObjectActionContributorManager();
        }
        return sharedInstance;
    }

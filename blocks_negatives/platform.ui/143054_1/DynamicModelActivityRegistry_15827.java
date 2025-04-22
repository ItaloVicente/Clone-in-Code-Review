    /**
     * Add default enablement to the provided activity
     * @param activityId the activity id
     */
    public void addDefaultEnabledActivity(String activityId) {
        if (defaultEnabledActivities.add(activityId)) {
            fireActivityRegistryChanged();
        }
    }

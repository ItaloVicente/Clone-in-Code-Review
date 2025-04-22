    /**
     * Remove default enablement to the provided activity
     *
     * @param activityId the activity id.
     */
    public void removeDefaultEnabledActivity(String activityId) {
        if (defaultEnabledActivities.remove(activityId)) {
            fireActivityRegistryChanged();
        }
    }

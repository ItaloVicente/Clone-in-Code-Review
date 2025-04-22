    /**
     * Add an activity.
     *
     * @param activityId
     *            The activity's id.
     * @param activityName
     *            The activity's name
     */
    public void addActivity(String activityId, String activityName) {
        activityDefinitions.add(new ActivityDefinition(activityId,
                activityName, sourceId, "description")); //$NON-NLS-1$
        fireActivityRegistryChanged();
    }

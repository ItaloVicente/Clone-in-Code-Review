    /**
     * Remove adn activity.
     *
     * @param activityId
     *            The activity's id.
     * @param activityName
     *            The activity's name.
     */
    public void removeActivity(String activityId, String activityName) {
        activityDefinitions.remove(new ActivityDefinition(activityId,
                activityName, sourceId, "description")); //$NON-NLS-1$
        fireActivityRegistryChanged();
    }

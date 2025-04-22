    /**
     * Add an activity pattern binding.
     *
     * @param activityId
     *            The actvity Id.
     * @param pattern
     *            The pattern.
     */
    public void addActivityPatternBinding(String activityId, String pattern) {
        if (activityPatternBindingDefinitions
                .add(new ActivityPatternBindingDefinition(activityId, pattern,
                        sourceId))) {
            fireActivityRegistryChanged();
            return;
        }
    }

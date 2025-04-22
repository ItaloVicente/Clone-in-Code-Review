    /**
     * Reomve an activity activity binding.
     *
     * @param parentId
     *            The parent id.
     * @param childId
     *            The child id.
     */
    public void removeActivityRequirementBinding(String childId, String parentId) {
        activityRequirementBindingDefinitions
                .remove(new ActivityRequirementBindingDefinition(childId,
                        parentId, sourceId));
        fireActivityRegistryChanged();
    }

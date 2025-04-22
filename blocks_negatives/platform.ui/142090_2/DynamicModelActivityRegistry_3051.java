    /**
     * Add an activity activity binding.
     *
     * @param parentId
     *            The parent id.
     * @param childId
     *            The child id.
     */
    public void addActivityRequirementBinding(String childId, String parentId) {
        activityRequirementBindingDefinitions
                .add(new ActivityRequirementBindingDefinition(childId,
                        parentId, sourceId));
        fireActivityRegistryChanged();
    }

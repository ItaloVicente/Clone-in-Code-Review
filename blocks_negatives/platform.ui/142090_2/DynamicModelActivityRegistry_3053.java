    /**
     * Add a category activity binding.
     *
     * @param activityId
     *            The activity id.
     * @param categoryId
     *            The category id.
     */
    public void addCategoryActivityBinding(String activityId, String categoryId) {
        categoryActivityBindingDefinitions
                .add(new CategoryActivityBindingDefinition(activityId,
                        categoryId, sourceId));
        fireActivityRegistryChanged();
    }

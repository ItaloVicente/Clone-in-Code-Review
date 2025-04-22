    /**
     * Remove a category activity binding.
     *
     * @param activityId
     *            The activity id.
     * @param categoryId
     *            The category id.
     */
    public void removeCategoryActivityBinding(String activityId,
            String categoryId) {
        categoryActivityBindingDefinitions
                .remove(new CategoryActivityBindingDefinition(activityId,
                        categoryId, sourceId));
        fireActivityRegistryChanged();
    }

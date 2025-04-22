    /**
     * Add a category.
     *
     * @param categoryId
     *            The category's id.
     * @param categoryName
     *            The category's name.
     */
    public void addCategory(String categoryId, String categoryName) {
        categoryDefinitions.add(new CategoryDefinition(categoryId,
                categoryName, sourceId, "description")); //$NON-NLS-1$
        fireActivityRegistryChanged();
    }

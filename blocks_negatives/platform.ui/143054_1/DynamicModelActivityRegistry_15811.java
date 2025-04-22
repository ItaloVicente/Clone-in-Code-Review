    /**
     * Remove a category.
     *
     * @param categoryId
     *            The category's id.
     * @param categoryName
     *            The category's name.
     */
    public void removeCategory(String categoryId, String categoryName) {
        categoryDefinitions.remove(new CategoryDefinition(categoryId,
                categoryName, sourceId, "description")); //$NON-NLS-1$
        fireActivityRegistryChanged();
    }

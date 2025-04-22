    }

    /**
     * Expands the wizard categories in this page's category viewer that were
     * expanded last time this page was used. If a category that was previously
     * expanded no longer exists then it is ignored.
     */
    protected void expandPreviouslyExpandedCategories() {
        String[] expandedCategoryPaths = settings
                .getArray(STORE_EXPANDED_CATEGORIES_ID);
        if (expandedCategoryPaths == null || expandedCategoryPaths.length == 0) {

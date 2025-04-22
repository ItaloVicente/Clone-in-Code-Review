        return children;
    }

    /**
     * Returns the child entries of the given entry
     * @param entry The entry to search
     *
     * @return the children of the given entry (element type
     *         <code>IPropertySheetEntry</code>)
     */
    private List getChildren(IPropertySheetEntry entry) {
        if (entry == rootEntry && isShowingCategories) {
            if (categories.length > 1
                    || (categories.length == 1 && !categories[0]
                            .getCategoryName().equals(
                                    MISCELLANEOUS_CATEGORY_NAME))) {

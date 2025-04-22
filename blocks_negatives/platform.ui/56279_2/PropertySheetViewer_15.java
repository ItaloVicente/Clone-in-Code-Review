        return tree;
    }

    /**
     * Returns the entries which match the current filter.
     *
     * @param entries the entries to filter
     * @return the entries which match the current filter
     *  (element type <code>IPropertySheetEntry</code>)
     */
    private List getFilteredEntries(IPropertySheetEntry[] entries) {
        if (isShowingExpertProperties) {

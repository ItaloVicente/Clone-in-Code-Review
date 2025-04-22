    public void setComparator(ViewerComparator comparator){
    	fComparator = comparator;
    }

    /**
     * Adds a filter to the tree viewer.
     *
     * @param filter
     *            a filter.
     */
    public void addFilter(ViewerFilter filter) {
        if (fFilters == null) {

    /**
     * Sets the sort order for this content provider
     *
     * @param sorter sort order
     */
    public void setSortOrder(Comparator sorter) {
    	Assert.isNotNull(sorter);
        this.sortOrder = sorter;
    	sortMon.cancel();
        refresh();
    }

    /**
     * Sets the filter for this content provider
     *
     * @param toSet filter to set
     */
    public void setFilter(IFilter toSet) {
    	Assert.isNotNull(toSet);
    	this.filter = toSet;
    	sortMon.cancel();
    	refresh();
    }

    /**
     * Sets the maximum table size. Based on the current sort order,
     * the table will be truncated if it grows beyond this size.
     * Using a limit improves memory usage and performance, and is
     * strongly recommended for large tables.
     *
     * @param limit maximum rows to show in the table or -1 if unbounded
     */
    public void setLimit(int limit) {
        this.limit = limit;
        refresh();
    }

    /**
     * Returns the maximum table size or -1 if unbounded
     *
     * @return the maximum number of rows in the table or -1 if unbounded
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Checks if currently visible range has changed, and triggers and update
     * and resort if necessary. Must be called in the UI thread, typically
     * within a SWT.SetData callback.
     * @param includeIndex the index that should be included in the visible range.
     */
    public void checkVisibleRange(int includeIndex) {
    	updator.checkVisibleRange(includeIndex);

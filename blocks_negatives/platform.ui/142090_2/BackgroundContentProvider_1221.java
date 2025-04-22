    /**
     * Creates a new background content provider
     *
     * @param table table that will receive updates
     * @param model data source
     * @param sortOrder initial sort order
     */
    public BackgroundContentProvider(AbstractVirtualTable table,
            IConcurrentModel model, Comparator sortOrder) {

        updator = new ConcurrentTableUpdator(table);
        this.model = model;
        this.sortOrder = sortOrder;
        model.addListener(listener);
    }

    /**
     * Cleans up this content provider, detaches listeners, frees up memory, etc.
     * Must be the last public method called on this object.
     */
    public void dispose() {
        cancelSortJob();
        updator.dispose();
        model.removeListener(listener);
    }

    /**
     * Force a refresh. Asks the model to re-send its complete contents.
     */
    public void refresh() {
    	if (updator.isDisposed()) {
    		return;
    	}
        model.requestUpdate(listener);
    }

    /**
     * Called from sortJob. Sorts the elements defined by sortStart and sortLength.
     * Schedules a UI update when finished.
     *
     * @param mon monitor where progress will be reported
     */
    private void doSort(IProgressMonitor mon) {

        mon.setCanceled(false);

       	mon.beginTask(SORTING, 100);

        Comparator order = sortOrder;
        IFilter f = filter;
        LazySortedCollection collection = new LazySortedCollection(order);

        Object[] knownObjects = updator.getKnownObjects();
        for (Object object : knownObjects) {

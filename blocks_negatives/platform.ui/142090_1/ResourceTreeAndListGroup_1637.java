    }

    /**
     * Add all of the selected children of nextEntry to result recursively.
     * This does not set any values in the checked state.
     * @param treeElement The tree elements being queried
     * @param addAll a boolean to indicate if the checked state store needs to be queried
     * @param filter IElementFilter - the filter being used on the data
     * @param monitor IProgressMonitor or null that the cancel is polled for
     */
    private void findAllSelectedListElements(Object treeElement, String parentLabel, boolean addAll, IElementFilter filter,
            IProgressMonitor monitor) throws InterruptedException {

        String fullLabel = null;
        if (monitor != null && monitor.isCanceled()) {

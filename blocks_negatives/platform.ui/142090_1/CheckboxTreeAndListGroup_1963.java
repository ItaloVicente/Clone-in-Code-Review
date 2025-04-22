    }

    /**
     *	Set the list viewer's providers to those passed
     *
     *	@param contentProvider ITreeContentProvider
     *	@param labelProvider ILabelProvider
     */
    public void setListProviders(IStructuredContentProvider contentProvider,
            ILabelProvider labelProvider) {
        listViewer.setContentProvider(contentProvider);
        listViewer.setLabelProvider(labelProvider);
    }

    /**
     *	Set the comparator that is to be applied to self's list viewer
     *
     *	@param comparator the comparator for the list viewer
     */
    public void setListComparator(ViewerComparator comparator) {
        listViewer.setComparator(comparator);
    }

    /**
     * Set the root of the widget to be new Root. Regenerate all of the tables and lists from this
     * value.
     * @param newRoot
     */
    public void setRoot(Object newRoot) {
        this.root = newRoot;
        initialize();
    }

    /**
     *	Set the checked state of the passed tree element appropriately, and
     *	do so recursively to all of its child tree elements as well
     */
    protected void setTreeChecked(Object treeElement, boolean state) {

        if (treeElement.equals(currentTreeSelection)) {
            listViewer.setAllChecked(state);
        }

        if (state) {
            List listItemsChecked = new ArrayList();

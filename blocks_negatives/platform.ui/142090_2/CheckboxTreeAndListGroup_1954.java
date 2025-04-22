    }

    /**
     * Cause the tree viewer to expand all its items
     */
    public void expandAll() {
        treeViewer.expandAll();
    }

    /**
     *	Answer a flat collection of all of the checked elements in the
     *	list portion of self
     *
     *	@return java.util.Vector
     */
    public Iterator getAllCheckedListItems() {
        List result = new ArrayList();
        Iterator listCollectionsEnum = checkedStateStore.values().iterator();

        while (listCollectionsEnum.hasNext()) {
            Iterator currentCollection = ((List) listCollectionsEnum.next())
                    .iterator();
            while (currentCollection.hasNext()) {

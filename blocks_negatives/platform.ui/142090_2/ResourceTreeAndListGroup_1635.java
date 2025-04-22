    }

    /**
     * Causes the tree viewer to expand all its items
     */
    public void expandAll() {
        treeViewer.expandAll();
    }

    /**
     * Causes the tree viewer to collapse all of its items
     */
    public void collapseAll() {
    	treeViewer.collapseAll();
    }

    /**
     *	Expand an element in a tree viewer
     */
    private void expandTreeElement(final Object item) {
        BusyIndicator.showWhile(treeViewer.getControl().getDisplay(),
                () -> {

				    if (expandedTreeNodes.contains(item)) {

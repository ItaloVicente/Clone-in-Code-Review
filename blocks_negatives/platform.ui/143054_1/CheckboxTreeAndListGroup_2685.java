        Object parent = treeContentProvider.getParent(treeElement);
        if (parent != null) {
            updateHierarchy(parent);
        }
    }

    /**
     * Update the selections of the tree elements in items to reflect the new
     * selections provided.
     * @param items Map with keys of Object (the tree element) and values of List (the selected
     * list elements).
     */
    public void updateSelections(final Map items) {

        BusyIndicator.showWhile(treeViewer.getControl().getDisplay(),
                () -> {

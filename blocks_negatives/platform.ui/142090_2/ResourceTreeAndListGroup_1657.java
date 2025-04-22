    }

    /**
     * Update the selections of the tree elements in items to reflect the new
     * selections provided.
     * @param items Map with keys of Object (the tree element) and values of List (the selected
     * list elements).
     * NOTE: This method does not special case keys with no values (i.e.,
     * a tree element with an empty list).  If a tree element does not have any selected
     * items, do not include the element in the Map.
     */
    public void updateSelections(Map items) {
        this.listViewer.setAllChecked(false);
        this.treeViewer.setCheckedElements(new Object[0]);
        this.whiteCheckedTreeItems = new HashSet();
        Set selectedNodes = new HashSet();
        checkedStateStore = new HashMap();


        currentTreeSelection = selectedElement;
    }

    /**
     * Select or deselect all of the elements in the tree depending on the value of the selection
     * boolean. Be sure to update the displayed files as well.
     *
     * @param selection boolean indicating whether or not to select all elements
     */
    public void setAllSelections(final boolean selection) {

        BusyIndicator.showWhile(treeViewer.getControl().getDisplay(),
                () -> {
				    setTreeChecked(root, selection);
				    listViewer.setAllChecked(selection);

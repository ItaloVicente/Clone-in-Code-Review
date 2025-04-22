    }

    /**
     * Deactivate the currently active cell editor.
     */
    /* package */
    void deactivateCellEditor() {
        treeEditor.setEditor(null, null, columnToEdit);
        if (cellEditor != null) {
            cellEditor.deactivate();
            fireCellEditorDeactivated(cellEditor);
            cellEditor.removeListener(editorListener);
            cellEditor = null;
        }
        setErrorMessage(null);
    }

    /**
     * Sends out a selection changed event for the entry tree to all registered
     * listeners.
     */
    private void entrySelectionChanged() {
        SelectionChangedEvent changeEvent = new SelectionChangedEvent(this,
                getSelection());
        fireSelectionChanged(changeEvent);
    }

    /**
     * Return a tree item in the property sheet that has the same entry in
     * its user data field as the supplied entry. Return <code>null</code> if
     * there is no such item.
     *
     * @param entry
     *            the entry to serach for
     * @return the TreeItem for the entry or <code>null</code> if
     * there isn't one.
     */
    private TreeItem findItem(IPropertySheetEntry entry) {
        TreeItem[] items = tree.getItems();
        for (int i = 0; i < items.length; i++) {
            TreeItem item = items[i];
            TreeItem findItem = findItem(entry, item);
            if (findItem != null) {

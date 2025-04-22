        if (rootEntry != null) {
            updateChildrenOf(rootEntry, tree);
        }
    }

    /**
     * Removes the given cell editor activation listener from this viewer. Has
     * no effect if an identical activation listener is not registered.
     *
     * @param listener
     *            a cell editor activation listener
     */
    /* package */
    void removeActivationListener(ICellEditorActivationListener listener) {
        activationListeners.remove(listener);
    }

    /**
     * Remove the given item from the tree. Remove our listener if the
     * item's user data is a an entry then set the user data to null
     *
     * @param item
     *            the item to remove
     */
    private void removeItem(TreeItem item) {
        Object data = item.getData();
        if (data instanceof IPropertySheetEntry) {

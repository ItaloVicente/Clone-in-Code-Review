        }
        return new StructuredSelection(entries);
    }

    /**
     * Selection in the viewer occurred. Check if there is an active cell
     * editor. If yes, deactivate it and check if a new cell editor must be
     * activated.
     *
     * @param selection
     *            the TreeItem that is selected
     */
    private void handleSelect(TreeItem selection) {
        if (cellEditor != null) {
            applyEditorValue();
            deactivateCellEditor();
        }

        if (selection == null) {
            setMessage(null);
            setErrorMessage(null);
        } else {
            Object object = selection.getData();
            if (object instanceof IPropertySheetEntry) {
                IPropertySheetEntry activeEntry = (IPropertySheetEntry) object;

                setMessage(activeEntry.getDescription());

                activateCellEditor(selection);
            }
        }
        entrySelectionChanged();
    }

    /**
     * The expand icon for a node in this viewer has been selected to collapse a
     * subtree. Deactivate the cell editor
     *
     * @param event
     *            the SWT tree event
     */
    private void handleTreeCollapse(TreeEvent event) {
        if (cellEditor != null) {
            applyEditorValue();
            deactivateCellEditor();
        }
    }

    /**
     * The expand icon for a node in this viewer has been selected to expand the
     * subtree. Create the children 1 level deep.
     * <p>
     * Note that we use a "dummy" item (no user data) to show a "+" icon beside
     * an item which has children before the item is expanded now that it is
     * being expanded we have to create the real child items
     * </p>
     *
     * @param event
     *            the SWT tree event
     */
    private void handleTreeExpand(TreeEvent event) {
        createChildren(event.item);
    }

    /**
     * Hides the categories.
     */
    /* package */
    void hideCategories() {
        isShowingCategories = false;
        categories = null;
        refresh();
    }

    /**
     * Hides the expert properties.
     */
    /* package */
    void hideExpert() {
        isShowingExpertProperties = false;
        refresh();
    }

    /**
     * Establish this viewer as a listener on the control
     */
    private void hookControl() {
        tree.addSelectionListener(new SelectionAdapter() {
            @Override

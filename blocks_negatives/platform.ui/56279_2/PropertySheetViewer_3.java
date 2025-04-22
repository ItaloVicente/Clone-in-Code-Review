        cellEditor.activate();

        Control control = cellEditor.getControl();
        if (control == null) {
            cellEditor.deactivate();
            cellEditor = null;
            return;
        }

        cellEditor.addListener(editorListener);

        CellEditor.LayoutData layout = cellEditor.getLayoutData();
        treeEditor.horizontalAlignment = layout.horizontalAlignment;
        treeEditor.grabHorizontal = layout.grabHorizontal;
        treeEditor.minimumWidth = layout.minimumWidth;
        treeEditor.setEditor(control, item, columnToEdit);

        setErrorMessage(cellEditor.getErrorMessage());

        cellEditor.setFocus();

        fireCellEditorActivated(cellEditor);
    }

    /**
     * Adds a cell editor activation listener. Has no effect if an identical
     * activation listener is already registered.
     *
     * @param listener
     *            a cell editor activation listener
     */
    /* package */
    void addActivationListener(ICellEditorActivationListener listener) {
        activationListeners.add(listener);
    }

    /**
     * Add columns to the tree and set up the layout manager accordingly.
     */
    private void addColumns() {

        TreeColumn[] columns = tree.getColumns();
        for (int i = 0; i < columnLabels.length; i++) {
            String string = columnLabels[i];
            if (string != null) {
                TreeColumn column;
                if (i < columns.length) {

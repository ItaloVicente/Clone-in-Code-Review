        }
        return null;
    }

    /**
     * Notifies all registered cell editor activation listeners of a cell editor
     * activation.
     *
     * @param activatedCellEditor
     *            the activated cell editor
     */
    private void fireCellEditorActivated(CellEditor activatedCellEditor) {
        Object[] listeners = activationListeners.getListeners();
        for (int i = 0; i < listeners.length; ++i) {
            ((ICellEditorActivationListener) listeners[i])
                    .cellEditorActivated(activatedCellEditor);
        }
    }

    /**
     * Notifies all registered cell editor activation listeners of a cell editor
     * deactivation.
     *
     * @param activatedCellEditor
     *            the deactivated cell editor
     */
    private void fireCellEditorDeactivated(CellEditor activatedCellEditor) {
        Object[] listeners = activationListeners.getListeners();
        for (int i = 0; i < listeners.length; ++i) {
            ((ICellEditorActivationListener) listeners[i])
                    .cellEditorDeactivated(activatedCellEditor);
        }
    }

    /**
     * Returns the active cell editor of this property sheet viewer or
     * <code>null</code> if no cell editor is active.
     *
     * @return the active cell editor
     */
    public CellEditor getActiveCellEditor() {
        return cellEditor;
    }

    private TreeItem[] getChildItems(Widget widget) {
        if (widget instanceof Tree) {
            return ((Tree) widget).getItems();
        }
        else if (widget instanceof TreeItem) {
            return ((TreeItem) widget).getItems();
        }
        return new TreeItem[0];
    }

    /**
     * Returns the sorted children of the given category or entry
     *
     * @param node a category or entry
     * @return the children of the given category or entry
     *  (element type <code>IPropertySheetEntry</code> or
     *  <code>PropertySheetCategory</code>)
     */
    private List getChildren(Object node) {
        IPropertySheetEntry entry = null;
        PropertySheetCategory category = null;
        if (node instanceof IPropertySheetEntry) {

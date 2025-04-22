    /**
     * Updates enablement based on the current selection.
     *
     * @param sel the selection
     */
    public void selectionChanged(IStructuredSelection sel) {
        setEnabled(!sel.isEmpty());
    }

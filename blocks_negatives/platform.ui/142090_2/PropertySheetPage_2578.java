    /**
     * Returns the cell editor activation listener for this page
     * @return ICellEditorActivationListener the cell editor activation listener for this page
     */
    private ICellEditorActivationListener getCellEditorActivationListener() {
        if (cellEditorActivationListener == null) {
            cellEditorActivationListener = new ICellEditorActivationListener() {
                @Override

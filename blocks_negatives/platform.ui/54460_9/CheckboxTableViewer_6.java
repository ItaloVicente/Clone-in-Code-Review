     * Creates a new table control with one column.
     *
     * @param parent the parent control
     * @param style style bits
     * @return a new table control
     */
    protected static Table createTable(Composite parent, int style) {
        Table table = new Table(parent, SWT.CHECK | style);

        new TableColumn(table, SWT.NONE);
        TableLayout layout = new TableLayout();
        layout.addColumnData(new ColumnWeightData(100));
        table.setLayout(layout);

        return table;
    }

    /**
     * Notifies any check state listeners that a check state changed  has been received.
     * Only listeners registered at the time this method is called are notified.
     *
     * @param event a check state changed event
     *
     * @see ICheckStateListener#checkStateChanged
     */
    private void fireCheckStateChanged(final CheckStateChangedEvent event) {
        Object[] array = checkStateListeners.getListeners();
        for (int i = 0; i < array.length; i++) {
            final ICheckStateListener l = (ICheckStateListener) array[i];
            SafeRunnable.run(new SafeRunnable() {
                @Override

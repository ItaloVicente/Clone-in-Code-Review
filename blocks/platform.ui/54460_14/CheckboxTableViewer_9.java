
	protected static Table createTable(Composite parent, int style) {
		Table table = new Table(parent, SWT.CHECK | style);

		new TableColumn(table, SWT.NONE);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(100));
		table.setLayout(layout);

		return table;
	}

	private void fireCheckStateChanged(final CheckStateChangedEvent event) {
		Object[] array = checkStateListeners.getListeners();
		for (int i = 0; i < array.length; i++) {
			final ICheckStateListener l = (ICheckStateListener) array[i];
			SafeRunnable.run(new SafeRunnable() {
				@Override

	private IListChangeListener listChangeListener = event -> {
		ListDiffEntry[] differences = event.diff.getDifferences();
		for (int i = 0; i < differences.length; i++) {
			ListDiffEntry entry = differences[i];
			if (entry.isAddition()) {
				TableItem item = new TableItem(table, SWT.NONE, entry
						.getPosition());
				UpdateRunnable updateRunnable = new UpdateRunnable(item, entry.getElement());
				item.setData(updateRunnable);
				updateRunnable.makeDirty();
			} else {
				table.getItem(entry.getPosition()).dispose();

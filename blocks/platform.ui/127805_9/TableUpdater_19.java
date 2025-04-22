	private IListChangeListener<E> listChangeListener = event -> {
		ListDiffEntry<? extends E>[] differences = event.diff.getDifferences();
		for (ListDiffEntry<? extends E> entry : differences) {
			if (entry.isAddition()) {
				TableItem item = new TableItem(table, SWT.NONE, entry.getPosition());
				UpdateRunnable updateRunnable = new UpdateRunnable(item, entry.getElement());
				item.setData(updateRunnable);
				updateRunnable.makeDirty();
			} else {
				table.getItem(entry.getPosition()).dispose();

	private IListChangeListener listChangeListener = new IListChangeListener() {
		@Override
		public void handleListChange(ListChangeEvent event) {
			ListDiffEntry[] differences = event.diff.getDifferences();
			for (ListDiffEntry entry : differences) {
				if (entry.isAddition()) {
					TableItem item = new TableItem(table, SWT.NONE, entry
							.getPosition());
					UpdateRunnable updateRunnable = new UpdateRunnable(item, entry.getElement());
					item.setData(updateRunnable);
					updateRunnable.makeDirty();
				} else {
					table.getItem(entry.getPosition()).dispose();
				}

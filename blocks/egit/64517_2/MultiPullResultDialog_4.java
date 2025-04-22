
	private ColumnComparator createComparator(TableColumn column,
			int columnIndex) {
		return new ColumnComparator(column, columnIndex);
	}

	private class ColumnComparator extends ViewerComparator {

		private static final int ASCENDING = SWT.DOWN;

		private static final int NONE = SWT.NONE;

		private static final int DESCENDING = SWT.UP;

		private final TableColumn column;

		private final int columnIndex;

		private int direction;

		public ColumnComparator(TableColumn column, int columnIndex) {
			super(null);
			this.column = column;
			this.columnIndex = columnIndex;
			column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (tv.getComparator() == ColumnComparator.this) {
						if (direction == ASCENDING) {
							setDirection(DESCENDING);
						} else {
							setDirection(NONE);
						}
					} else {
						setDirection(ASCENDING);
					}
				}
			});
		}

		private void setDirection(int newDirection) {
			direction = newDirection;
			Table table = column.getParent();
			table.setSortDirection(direction);
			if (direction == NONE) {
				table.setSortColumn(null);
				tv.setComparator(null);
			} else {
				table.setSortColumn(column);
				if (tv.getComparator() == this) {
					tv.refresh();
				} else {
					tv.setComparator(this);
				}
			}
		}

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			ColumnLabelProvider labelProvider = (ColumnLabelProvider) tv
					.getLabelProvider(columnIndex);
			String label1 = labelProvider.getText(e1);
			String label2 = labelProvider.getText(e2);
			int result = label1.compareTo(label2);
			return direction == DESCENDING ? -result : result;
		}
	}

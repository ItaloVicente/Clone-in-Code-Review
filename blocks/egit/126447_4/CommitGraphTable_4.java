	void setVisible(int columnIndex, boolean visible) {
		Assert.isLegal(columnIndex >= 0 && columnIndex < columnLayouts.length);
		if (columnIndex == 1) {
			return; // Commit message column is always visible
		}
		Table rawTable = table.getTable();
		TableColumn col = rawTable.getColumn(columnIndex);
		boolean isVisible = col.getWidth() > 0;
		if (isVisible != visible) {
			if (isVisible) {
				columnLayouts[columnIndex] = tableLayout.getLayoutData(rawTable,
						columnIndex);
				if (columnLayouts[columnIndex] == null) {
					columnLayouts[columnIndex] = defaultLayouts[columnIndex];
				}
				tableLayout.setColumnData(col, new ColumnPixelData(0));
			} else {
				tableLayout.setColumnData(col, columnLayouts[columnIndex]);
			}
			rawTable.getParent().layout();
		}
	}

	private void setColumn(TableColumn column, int index, boolean visible) {
		if (visible) {
			baseLayouts[index] = defaultLayouts[index];
			if (column.getWidth() > 0) {
				return;
			}
			tableLayout.setColumnData(column, columnLayouts[index]);
		} else {
			baseLayouts[index] = new ColumnPixelData(0);
			tableLayout.setColumnData(column, baseLayouts[index]);
		}
	}

	private void applyColumnPreferences(IPreferenceStore store,
			Table rawTable) {
		setColumn(rawTable.getColumn(0), 0,
				store.getBoolean(UIPreferences.HISTORY_COLUMN_ID));
		setColumn(rawTable.getColumn(2), 2,
				store.getBoolean(UIPreferences.HISTORY_COLUMN_AUTHOR));
		setColumn(rawTable.getColumn(3), 3,
				store.getBoolean(UIPreferences.HISTORY_COLUMN_AUTHOR_DATE));
		setColumn(rawTable.getColumn(4), 4,
				store.getBoolean(UIPreferences.HISTORY_COLUMN_COMMITTER));
		setColumn(rawTable.getColumn(5), 5,
				store.getBoolean(UIPreferences.HISTORY_COLUMN_COMMITTER_DATE));
	}

	private static class CommitGraphTableLayout extends TableColumnLayout {

		@Override
		protected ColumnLayoutData getLayoutData(Scrollable tableTree,
				int columnIndex) {
			return super.getLayoutData(tableTree, columnIndex);
		}
	}



	private void createColumns(final Table rawTable, final TableLayout layout) {
		final TableColumn path = new TableColumn(rawTable, SWT.NONE);
		path.setResizable(true);
		path.setText(UIText.HistoryPage_pathnameColumn);
		layout.addColumnData(new ColumnWeightData(1, true));
	}

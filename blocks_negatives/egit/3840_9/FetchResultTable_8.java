	private TableViewerColumn createColumn(
			final TableColumnLayout columnLayout, final String text,
			final int weight, final int style) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(
				tableViewer, style);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(text);
		columnLayout.setColumnData(column, new ColumnWeightData(weight));
		return viewerColumn;

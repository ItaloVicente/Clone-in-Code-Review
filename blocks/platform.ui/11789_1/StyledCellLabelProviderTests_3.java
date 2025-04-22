	private void createColumn(TableViewer viewer, int style, CellLabelProvider labelProvider) {
		column = new TableViewerColumn(viewer, style);
		column.getColumn().setWidth(200);
		column.getColumn().setText("Column");
		column.setLabelProvider(labelProvider);
		viewer.refresh();
	}
	

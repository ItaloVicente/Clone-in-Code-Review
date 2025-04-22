
		TableColumn column = new TableColumn(tableViewer.getTable(), SWT.NONE);
		column.setWidth(200);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, column);
		tableViewerColumn.setEditingSupport(new EditingSupport(tableViewer) {


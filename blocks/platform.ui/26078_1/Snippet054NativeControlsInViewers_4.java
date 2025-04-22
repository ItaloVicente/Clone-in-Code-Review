	private TableViewerColumn createColumnFor(final TableViewer viewer,
			String columnLabel) {
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(200);
		column.getColumn().setText(columnLabel);
		return column;

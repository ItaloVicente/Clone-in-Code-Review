	private TableViewerColumn createColumnFor(TableViewer viewer, String label) {
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(200);
		column.getColumn().setText(label);
		column.getColumn().setMoveable(true);
		return column;
	}


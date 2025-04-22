	private TableViewerColumn createColumnFor(final TableViewer v, String label) {
		TableViewerColumn column;
		column = new TableViewerColumn(v, SWT.NONE);
		column.getColumn().setWidth(200);
		column.getColumn().setText(label);
		column.getColumn().setMoveable(true);
		return column;
	}


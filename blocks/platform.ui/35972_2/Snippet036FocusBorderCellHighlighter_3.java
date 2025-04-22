	private void createColumnFor(TableViewer v, String label, int columnIndex) {

		TableViewerColumn viewerColumn = new TableViewerColumn(v, SWT.NONE);
		viewerColumn.getColumn().setWidth(200);
		viewerColumn.getColumn().setMoveable(true);
		viewerColumn.getColumn().setText(label);

		viewerColumn.setEditingSupport(new MyEditingSupport(v, columnIndex + ""));
		viewerColumn.setLabelProvider(new MyColumnLabelProvider(v.getTable(), columnIndex));

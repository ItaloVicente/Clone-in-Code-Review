
		TableViewerColumn viewerColumn = new TableViewerColumn(v, SWT.NONE);
		viewerColumn.getColumn().setText("Column1");
		viewerColumn.getColumn().setWidth(300);
		viewerColumn.setLabelProvider(new ColumnLabelProvider());
		viewerColumn.setEditingSupport(new EditingSupport(v) {

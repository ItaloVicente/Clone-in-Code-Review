	private TreeColumn createColumn(String name, TreeViewer treeViewer, ColumnLabelProvider labelProvider) {
		TreeColumn column = new TreeColumn(treeViewer.getTree(), SWT.LEFT);
		column.setAlignment(SWT.LEFT);
		column.setText(name);

		TreeViewerColumn treeViewerNameColumn = new TreeViewerColumn(treeViewer, column);
		treeViewerNameColumn.setLabelProvider(labelProvider);
		return column;
	}


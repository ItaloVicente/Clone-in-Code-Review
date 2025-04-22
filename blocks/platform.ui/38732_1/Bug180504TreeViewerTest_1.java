		TreeColumn column = new TreeColumn(treeViewer.getTree(), SWT.NONE);
		column.setWidth(200);

		TreeViewerColumn tableViewerColumn = new TreeViewerColumn(treeViewer, column);
		tableViewerColumn.setEditingSupport(new EditingSupport(treeViewer) {


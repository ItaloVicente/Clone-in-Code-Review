		TreeColumn column = new TreeColumn(treeViewer.getTree(), SWT.NONE);
		column.setWidth(200);

		TreeViewerColumn viewerColumn = new TreeViewerColumn(treeViewer, column);
		viewerColumn.setEditingSupport(new EditingSupport(treeViewer) {


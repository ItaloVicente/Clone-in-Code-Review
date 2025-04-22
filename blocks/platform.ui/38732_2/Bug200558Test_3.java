
		TreeColumn column = new TreeColumn(treeViewer.getTree(), SWT.NONE);
		new TreeColumn(treeViewer.getTree(), SWT.NONE).setWidth(100);

		TreeViewerColumn treeViewerColumn = new TreeViewerColumn(treeViewer, column);
		treeViewerColumn.setEditingSupport(new EditingSupport(treeViewer) {


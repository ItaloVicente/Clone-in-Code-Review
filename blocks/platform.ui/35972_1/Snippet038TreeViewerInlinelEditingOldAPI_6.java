	private void createColumnFor(TreeViewer viewer, String label, int columnIndex) {
		TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
		viewerColumn.getColumn().setWidth(200);
		viewerColumn.getColumn().setText(label);

		viewerColumn.setEditingSupport(new MyEditingSupport(viewer));
		viewerColumn.setLabelProvider(new MyColumnLabelProvider(viewer.getTree(), columnIndex));
	}


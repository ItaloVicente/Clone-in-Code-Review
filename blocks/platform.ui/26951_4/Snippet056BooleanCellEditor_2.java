	private TreeViewerColumn createColumnFor(final TreeViewer viewer,
			String label) {
		TreeViewerColumn column = new TreeViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(200);
		column.getColumn().setMoveable(true);
		column.getColumn().setText(label);
		return column;
	}


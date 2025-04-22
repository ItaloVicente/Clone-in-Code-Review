	private TreeViewerColumn createColumnFor(TreeViewer viewer, int style,
			String label) {
		TreeViewerColumn column = new TreeViewerColumn(viewer, style);
		column.getColumn().setWidth(200);
		column.getColumn().setMoveable(true);
		column.getColumn().setText(label);
		return column;

		TreeViewerColumn commitMessageColumn = new TreeViewerColumn(
				planTreeViewer, SWT.NONE);
		commitMessageColumn.getColumn().setText(headings[3]);
		commitMessageColumn.getColumn().setMoveable(false);
		commitMessageColumn.getColumn().setResizable(true);
		commitMessageColumn.getColumn().setWidth(200);


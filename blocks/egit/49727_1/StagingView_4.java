
		TreeViewerColumn columnName = new TreeViewerColumn(treeViewer, SWT.LEFT);
		columnName.getColumn().setText("File"); //$NON-NLS-1$
		TreeViewerColumn columnModified = new TreeViewerColumn(treeViewer,
				SWT.LEFT);
		columnModified.getColumn().setText("Modified"); //$NON-NLS-1$

		tree.setHeaderVisible(true);
		tree.getColumn(0).setWidth(250);
		tree.getColumn(1).setWidth(100);


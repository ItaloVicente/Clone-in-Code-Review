
		TreeViewerColumn columnName = new TreeViewerColumn(treeViewer, SWT.LEFT);
		columnName.getColumn().setText(UIText.StagingView_Column_File);
		TreeViewerColumn columnModified = new TreeViewerColumn(treeViewer,
				SWT.LEFT);
		columnModified.getColumn().setText(UIText.StagingView_Column_Modified);

		tree.setHeaderVisible(true);
		tree.getColumn(0).setWidth(250);
		tree.getColumn(1).setWidth(100);


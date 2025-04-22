		TableViewerColumn idColumn = new TableViewerColumn(viewer, SWT.LEFT);
		idColumn.getColumn().setText(UIText.MergeResultDialog_id);
		idColumn.getColumn().setWidth(100);
		TableViewerColumn textColumn = new TableViewerColumn(viewer, SWT.LEFT);
		textColumn.getColumn().setText(UIText.MergeResultDialog_description);
		textColumn.getColumn().setWidth(300);

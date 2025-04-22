		editorTable = new FilteredTree(contents, SWT.SINGLE | SWT.BORDER, new PatternFilter(), true);
		editorTableViewer = editorTable.getViewer();
		Tree tree = editorTableViewer.getTree();
		tree.addListener(SWT.Selection, listener);
		tree.addListener(SWT.DefaultSelection, listener);
		tree.addListener(SWT.MouseDoubleClick, listener);

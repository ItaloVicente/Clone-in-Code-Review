		root = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(root);

		treeViewer = new TreeViewer(root);
		treeViewer.setAutoExpandLevel(2);

		addToolbar(root);

		ColumnViewerToolTipSupport.enableFor(treeViewer);
		final Tree table = treeViewer.getTree();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(table);

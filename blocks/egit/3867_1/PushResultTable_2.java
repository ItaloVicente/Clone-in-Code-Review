		root = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(root);
		treeViewer = new TreeViewer(root);
		treeViewer.setAutoExpandLevel(2);
		ColumnViewerToolTipSupport.enableFor(treeViewer);
		final Tree table = treeViewer.getTree();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(table);

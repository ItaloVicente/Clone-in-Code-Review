		final TreeColumnLayout layout = new TreeColumnLayout();

		FilteredTree filteredTree = new FilteredTree(tableComposite, SWT.NONE | SWT.BORDER, new PatternFilter(), true) {
			@Override
			protected void createControl(Composite composite, int treeStyle) {
				super.createControl(composite, treeStyle);
				treeComposite.setLayout(layout);
			}
		};

		toolkit.adapt(filteredTree);
		refLogTableTreeViewer = filteredTree.getViewer();
		refLogTableTreeViewer.getTree().setLinesVisible(true);
		refLogTableTreeViewer.getTree().setHeaderVisible(true);
		refLogTableTreeViewer.setContentProvider(new ReflogViewContentProvider());

		ColumnViewerToolTipSupport.enableFor(refLogTableTreeViewer);

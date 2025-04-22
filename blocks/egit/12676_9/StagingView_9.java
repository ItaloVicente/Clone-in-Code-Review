	private TreeViewer createTree(Composite composite) {
		Tree tree = toolkit.createTree(composite, SWT.FULL_SELECTION
				| SWT.MULTI);
		tree.setLinesVisible(true);
		TreeViewer treeViewer = new TreeViewer(tree);
		return treeViewer;
	}

	private IBaseLabelProvider createLabelProvider(TreeViewer treeViewer) {
		StagingViewLabelProvider baseProvider = new StagingViewLabelProvider(
				this);

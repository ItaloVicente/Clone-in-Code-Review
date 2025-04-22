	private void setExpandCollapseActionsVisible(boolean visible) {
		for (IContributionItem item : unstagedToolBarManager.getItems())
			item.setVisible(visible);
		for (IContributionItem item : stagedToolBarManager.getItems())
			item.setVisible(visible);
		unstagedExpandAllAction.setEnabled(visible);
		unstagedCollapseAllAction.setEnabled(visible);
		stagedExpandAllAction.setEnabled(visible);
		stagedCollapseAllAction.setEnabled(visible);
		unstagedToolBarManager.update(true);
		stagedToolBarManager.update(true);
	}

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

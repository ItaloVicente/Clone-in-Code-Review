	void setInput(StagingViewUpdate input) {
		if ((treeViewer.getControl().getStyle() & SWT.VIRTUAL) == 0) {
			treeViewer.setInput(input);
			return;
		}
		final Tree tree = treeViewer.getTree();
		inputChanged(treeViewer, tree.getData(), input);
		tree.setData(input);
		tree.setItemCount(getCount());
	}

	StagingViewUpdate getInput() {
		if ((treeViewer.getControl().getStyle() & SWT.VIRTUAL) == 0) {
			return (StagingViewUpdate) treeViewer.getInput();
		}
		return (StagingViewUpdate) treeViewer.getTree().getData();
	}


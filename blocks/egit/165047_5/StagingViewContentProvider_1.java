	void setInput(StagingViewUpdate input) {
		if (!haveVirtualTree()) {
			treeViewer.setInput(input);
			return;
		}
		final Tree tree = treeViewer.getTree();
		inputChanged(treeViewer, tree.getData(), input);
		tree.setData(input);
		internalRedraw();
	}

	StagingViewUpdate getInput() {
		if (!haveVirtualTree()) {
			return (StagingViewUpdate) treeViewer.getInput();
		}
		return (StagingViewUpdate) treeViewer.getTree().getData();
	}


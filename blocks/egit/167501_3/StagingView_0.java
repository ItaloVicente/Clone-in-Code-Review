		StagingViewContentProvider provider;
		if ((treeViewer.getTree().getStyle() & SWT.VIRTUAL) == 0) {
			provider = new StagingViewContentProvider(
					this, treeViewer, unstaged) {

				@Override
				public void inputChanged(Viewer viewer, Object oldInput,
						Object newInput) {
					super.inputChanged(viewer, oldInput, newInput);
					if (unstaged) {
						stageAllAction.setEnabled(getCount() > 0);
						unstagedToolBarManager.update(true);
					} else {
						unstageAllAction.setEnabled(getCount() > 0);
						stagedToolBarManager.update(true);
					}

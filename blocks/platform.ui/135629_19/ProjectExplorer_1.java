	@Override
	protected CommonViewer createCommonViewer(Composite aParent) {
		CommonViewer viewer = super.createCommonViewer(aParent);
		emptyWorkspaceHelper.setNonEmptyControl(viewer.getControl());
		return viewer;
	}


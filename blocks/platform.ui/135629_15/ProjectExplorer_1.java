	@Override
	protected CommonViewer createCommonViewer(Composite aParent) {
		CommonViewer viewer = super.createCommonViewer(aParent);
		emptyWorkspaceHelper.setControl(viewer.getControl());
		return viewer;
	}

	@Override
	public void dispose() {
		emptyWorkspaceHelper.dispose();
		super.dispose();
	}


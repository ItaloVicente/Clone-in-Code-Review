		fFilterActionGroup = new ProjectExplorerFilterActionGroup(pCommonViewer);
		return fFilterActionGroup;
	}

	@Override
	protected void fillToolBar(IToolBarManager pToolBar) {
		super.fillToolBar(pToolBar);
		fFilterActionGroup.fillToolbar(pToolBar);

	@Override
	public void init(IPageSite pageSite) {
		super.init(pageSite);
		addToolbarActions(pageSite.getActionBars().getToolBarManager());
	}

	private void addToolbarActions(IToolBarManager toolbarManager) {
		Action collapseAction = new Action(UIText.UIUtils_CollapseAll,
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
						ISharedImages.IMG_ELCL_COLLAPSEALL)) {
			@Override
			public void run() {
				getTreeViewer().collapseAll();
			}
		};
		toolbarManager.add(collapseAction);
	}


	@Override
	public void setActionBars(IActionBars actionBars) {
		super.setActionBars(actionBars);
		addToolbarActions(actionBars.getToolBarManager());
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
		collapseAction.setActionDefinitionId(CollapseAllHandler.COMMAND_ID);
		collapseHandler = new CollapseAllHandler(getTreeViewer());
		IHandlerService handlerService = getSite()
				.getService(IHandlerService.class);
		handlerService.activateHandler(CollapseAllHandler.COMMAND_ID,
				collapseHandler);
		toolbarManager.add(collapseAction);
	}

	@Override
	public void dispose() {
		if (collapseHandler != null) {
			collapseHandler.dispose();
		}
		super.dispose();
	}


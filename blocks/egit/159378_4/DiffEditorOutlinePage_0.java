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
				UIUtils.collapseAll(getTreeViewer());
			}
		};
		collapseAction.setActionDefinitionId(
				IWorkbenchCommandConstants.NAVIGATE_COLLAPSE_ALL);
		collapseHandler = new ActionHandler(collapseAction);
		IHandlerService handlerService = getSite()
				.getService(IHandlerService.class);
		handlerService.activateHandler(collapseAction.getActionDefinitionId(),
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


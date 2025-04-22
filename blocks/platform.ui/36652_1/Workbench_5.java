
	private void createApplicationMenu(Menu appMenu) {

		applicationMenuMgr = new ApplicationMenuManager(appMenu);
		IMenuService menuService = (IMenuService) serviceLocator.getService(IMenuService.class);
		menuService.populateContributionManager(applicationMenuMgr, MenuUtil.APPLICATION_MENU);
		applicationMenuMgr.update(true);
	}

	private void disposeApplicationMenu() {

		if (applicationMenuMgr == null)
			return;
		IMenuService menuService = (IMenuService) serviceLocator.getService(IMenuService.class);
		menuService.releaseContributions(applicationMenuMgr);
		applicationMenuMgr.dispose();
	}

	private void activateWorkbenchContext() {
		IContextService contextService = (IContextService) serviceLocator.getService(IContextService.class);
		workbenchContext = contextService.activateContext(IContextService.CONTEXT_ID_WORKBENCH);
	}

	private void deactivateWorkbenchContext() {
		if (workbenchContext == null)
			return;
		workbenchContext.getContextService().deactivateContext(workbenchContext);
	}

	private Menu getAppMenu() {
		IWorkbench workbench = getWorkbenchConfigurer().getWorkbench();
		return workbench.getDisplay().getMenuBar();
	}

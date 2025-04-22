	private MenuManager addPerspectiveActions() {
		MenuManager menu = new MenuManager(IDEWorkbenchMessages.Workbench_perspective,
				IWorkbenchActionConstants.M_PERSPECTIVE);
		menu.add(new GroupMarker(IWorkbenchActionConstants.PERSPECTIVE_START));

		String openText = IDEWorkbenchMessages.Workbench_openPerspective;
		MenuManager changePerspMenuMgr = new MenuManager(openText, "openPerspective"); //$NON-NLS-1$
		IContributionItem changePerspMenuItem = ContributionItemFactory.PERSPECTIVES_SHORTLIST.create(getWindow());
		changePerspMenuMgr.add(changePerspMenuItem);
		menu.add(changePerspMenuMgr);

		MenuManager showViewMenuMgr = new MenuManager(IDEWorkbenchMessages.Workbench_showView, "showView"); //$NON-NLS-1$
		IContributionItem showViewMenu = ContributionItemFactory.VIEWS_SHORTLIST.create(getWindow());
		showViewMenuMgr.add(showViewMenu);
		menu.add(showViewMenuMgr);

		menu.add(new Separator());
		menu.add(editActionSetAction);
		menu.add(getSavePerspectiveItem());
		menu.add(getResetPerspectiveItem());
		menu.add(closePerspAction);
		menu.add(closeAllPerspsAction);

		return menu;
	}

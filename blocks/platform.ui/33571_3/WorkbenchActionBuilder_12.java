	private MenuManager addPerspectiveActions() {
		MenuManager menu = new MenuManager(IDEWorkbenchMessages.Workbench_perspective,
				IWorkbenchActionConstants.M_PERSPECTIVE);
		menu.add(new GroupMarker(IWorkbenchActionConstants.PERSPECTIVE_START));

		String openText = IDEWorkbenchMessages.Workbench_openPerspective;
		MenuManager changePerspMenuMgr = new MenuManager(openText, "openPerspective"); //$NON-NLS-1$
		IContributionItem changePerspMenuItem = ContributionItemFactory.PERSPECTIVES_SHORTLIST.create(getWindow());
		changePerspMenuMgr.add(changePerspMenuItem);
		menu.add(changePerspMenuMgr);

		menu.add(new Separator());
		menu.add(editActionSetAction);
		menu.add(getSavePerspectiveItem());
		menu.add(getResetPerspectiveItem());
		menu.add(closePerspAction);
		menu.add(closeAllPerspsAction);

		return menu;
	}

	private void addWorkingSetBuildActions(MenuManager menu) {
		buildWorkingSetMenu = new MenuManager(IDEWorkbenchMessages.Workbench_buildSet);
		IContributionItem workingSetBuilds = new BuildSetMenu(window, getActionBarConfigurer());
		buildWorkingSetMenu.add(workingSetBuilds);
		menu.add(buildWorkingSetMenu);
	}

	private void addKeyboardShortcuts(MenuManager menu) {
		MenuManager subMenu = new MenuManager(IDEWorkbenchMessages.Workbench_shortcuts, "shortcuts"); //$NON-NLS-1$
		menu.add(subMenu);
		subMenu.add(showPartPaneMenuAction);
		subMenu.add(showViewMenuAction);
		subMenu.add(quickAccessAction);
		subMenu.add(new Separator());
		subMenu.add(maximizePartAction);
		subMenu.add(minimizePartAction);
		subMenu.add(new Separator());
		subMenu.add(activateEditorAction);
		subMenu.add(nextEditorAction);
		subMenu.add(prevEditorAction);
		subMenu.add(switchToEditorAction);
		subMenu.add(new Separator());
		subMenu.add(nextPartAction);
		subMenu.add(prevPartAction);
		subMenu.add(new Separator());
		subMenu.add(nextPerspectiveAction);
		subMenu.add(prevPerspectiveAction);
	}


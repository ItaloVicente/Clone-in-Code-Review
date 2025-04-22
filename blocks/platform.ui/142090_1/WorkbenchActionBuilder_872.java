	private void addWorkingSetBuildActions(MenuManager menu) {
		buildWorkingSetMenu = new MenuManager(IDEWorkbenchMessages.Workbench_buildSet);
		IContributionItem workingSetBuilds = new BuildSetMenu(window,
				getActionBarConfigurer());
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


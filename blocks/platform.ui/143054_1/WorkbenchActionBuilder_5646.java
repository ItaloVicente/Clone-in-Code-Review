		for (int i = 2; i < 5; ++i) {
			menu.add(new Separator(IWorkbenchActionConstants.SHOW_EXT + i));
		}
		menu.add(new Separator());
		menu.add(nextAction);
		menu.add(previousAction);
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menu.add(new GroupMarker(IWorkbenchActionConstants.NAV_END));

		menu.add(new Separator());
		menu.add(backwardHistoryAction);
		menu.add(forwardHistoryAction);
		return menu;
	}

	private MenuManager createProjectMenu() {
		MenuManager menu = new MenuManager(
				IDEWorkbenchMessages.Workbench_project, IWorkbenchActionConstants.M_PROJECT);
		menu.add(new Separator(IWorkbenchActionConstants.PROJ_START));

		menu.add(getOpenProjectItem());
		menu.add(getCloseProjectItem());
		menu.add(new GroupMarker(IWorkbenchActionConstants.OPEN_EXT));
		menu.add(new Separator());
		menu.add(buildAllAction);
		menu.add(buildProjectAction);
		addWorkingSetBuildActions(menu);
		menu.add(cleanAction);
		menu.add(toggleAutoBuildAction);
		menu.add(new GroupMarker(IWorkbenchActionConstants.BUILD_EXT));
		menu.add(new Separator());

		menu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		menu.add(new GroupMarker(IWorkbenchActionConstants.PROJ_END));
		menu.add(new Separator());
		menu.add(projectPropertyDialogAction);
		return menu;
	}

	private MenuManager createWindowMenu() {
		MenuManager menu = new MenuManager(
				IDEWorkbenchMessages.Workbench_window, IWorkbenchActionConstants.M_WINDOW);

		addMacWindowMenuItems(menu);

		menu.add(newWindowAction);

		menu.add(new Separator());

		menu.add(addShowView());

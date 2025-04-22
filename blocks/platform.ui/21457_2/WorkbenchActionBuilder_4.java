		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				resourceListener, IResourceChangeEvent.POST_CHANGE);
	}

	public void fillActionBars(int flags) {
		super.fillActionBars(flags);
		updateBuildActions(true);
		if ((flags & FILL_PROXY) == 0) {
			hookListeners();
		}
	}

	protected void fillCoolBar(ICoolBarManager coolBar) {

		IActionBarConfigurer2 actionBarConfigurer = (IActionBarConfigurer2) getActionBarConfigurer();
		{ // Set up the context Menu
			coolbarPopupMenuManager = new MenuManager();
			coolbarPopupMenuManager.add(new ActionContributionItem(
					lockToolBarAction));
			coolbarPopupMenuManager.add(new ActionContributionItem(
					editActionSetAction));
			coolBar.setContextMenuManager(coolbarPopupMenuManager);
			IMenuService menuService = (IMenuService) window
					.getService(IMenuService.class);
			menuService.populateContributionManager(coolbarPopupMenuManager,
					"popup:windowCoolbarContextMenu"); //$NON-NLS-1$
		}
		coolBar.add(new GroupMarker(IIDEActionConstants.GROUP_FILE));
		{ // File Group
			IToolBarManager fileToolBar = actionBarConfigurer
					.createToolBarManager();
			fileToolBar.add(new Separator(IWorkbenchActionConstants.NEW_GROUP));
			fileToolBar.add(newWizardDropDownAction);
			fileToolBar.add(new GroupMarker(IWorkbenchActionConstants.NEW_EXT));
			fileToolBar.add(new GroupMarker(
					IWorkbenchActionConstants.SAVE_GROUP));
			fileToolBar.add(saveAction);
			fileToolBar.add(saveAllAction);
			fileToolBar
					.add(new GroupMarker(IWorkbenchActionConstants.SAVE_EXT));

			IContributionItem printItem = getPrintItem();
			fileToolBar.add(printItem);
			printItem.setVisible(false);

			fileToolBar
					.add(new GroupMarker(IWorkbenchActionConstants.PRINT_EXT));

			fileToolBar
					.add(new Separator(IWorkbenchActionConstants.BUILD_GROUP));
			fileToolBar
					.add(new GroupMarker(IWorkbenchActionConstants.BUILD_EXT));
			fileToolBar.add(new Separator(
					IWorkbenchActionConstants.MB_ADDITIONS));

			coolBar.add(actionBarConfigurer.createToolBarContributionItem(
					fileToolBar, IWorkbenchActionConstants.TOOLBAR_FILE));
		}

		coolBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

		coolBar.add(new GroupMarker(IIDEActionConstants.GROUP_NAV));
		{ // Navigate group
			IToolBarManager navToolBar = actionBarConfigurer
					.createToolBarManager();
			navToolBar.add(new Separator(
					IWorkbenchActionConstants.HISTORY_GROUP));
			navToolBar
					.add(new GroupMarker(IWorkbenchActionConstants.GROUP_APP));
			navToolBar.add(backwardHistoryAction);
			navToolBar.add(forwardHistoryAction);
			navToolBar.add(new Separator(IWorkbenchActionConstants.PIN_GROUP));
			navToolBar.add(getPinEditorItem());

			coolBar.add(actionBarConfigurer.createToolBarContributionItem(
					navToolBar, IWorkbenchActionConstants.TOOLBAR_NAVIGATE));
		}

		coolBar.add(new GroupMarker(IWorkbenchActionConstants.GROUP_EDITOR));

		coolBar.add(new GroupMarker(IWorkbenchActionConstants.GROUP_HELP));

		{ // Help group
			IToolBarManager helpToolBar = actionBarConfigurer
					.createToolBarManager();
			helpToolBar
					.add(new Separator(IWorkbenchActionConstants.GROUP_HELP));
			helpToolBar
					.add(new GroupMarker(IWorkbenchActionConstants.GROUP_APP));
			coolBar.add(actionBarConfigurer.createToolBarContributionItem(
					helpToolBar, IWorkbenchActionConstants.TOOLBAR_HELP));
		}

	}

	protected void fillMenuBar(IMenuManager menuBar) {
		menuBar.add(createFileMenu());
		menuBar.add(createEditMenu());
		menuBar.add(createNavigateMenu());
		menuBar.add(createProjectMenu());
		menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		menuBar.add(createWindowMenu());
		menuBar.add(createHelpMenu());
	}

	private MenuManager createFileMenu() {
		MenuManager menu = new MenuManager(IDEWorkbenchMessages.Workbench_file,
				IWorkbenchActionConstants.M_FILE);
		menu.add(new GroupMarker(IWorkbenchActionConstants.FILE_START));
		{
			String newText = IDEWorkbenchMessages.Workbench_new;
			String newId = ActionFactory.NEW.getId();
			MenuManager newMenu = new MenuManager(newText, newId);
			newMenu.setActionDefinitionId("org.eclipse.ui.file.newQuickMenu"); //$NON-NLS-1$
			newMenu.add(new Separator(newId));
			this.newWizardMenu = new NewWizardMenu(getWindow());
			newMenu.add(this.newWizardMenu);
			newMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			menu.add(newMenu);
		}

		menu.add(new GroupMarker(IWorkbenchActionConstants.NEW_EXT));
		menu.add(new Separator());

		menu.add(closeAction);
		menu.add(closeAllAction);
		menu.add(new GroupMarker(IWorkbenchActionConstants.CLOSE_EXT));
		menu.add(new Separator());
		menu.add(saveAction);
		menu.add(saveAsAction);
		menu.add(saveAllAction);
		menu.add(getRevertItem());
		menu.add(new Separator());
		menu.add(getMoveItem());
		menu.add(getRenameItem());
		menu.add(getRefreshItem());

		menu.add(new GroupMarker(IWorkbenchActionConstants.SAVE_EXT));
		menu.add(new Separator());
		menu.add(getPrintItem());
		menu.add(new GroupMarker(IWorkbenchActionConstants.PRINT_EXT));
		menu.add(new Separator());
		menu.add(openWorkspaceAction);
		menu.add(new GroupMarker(IWorkbenchActionConstants.OPEN_EXT));
		menu.add(new Separator());
		menu.add(importResourcesAction);
		menu.add(exportResourcesAction);
		menu.add(new GroupMarker(IWorkbenchActionConstants.IMPORT_EXT));
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

		menu.add(new Separator());
		menu.add(getPropertiesItem());

		menu.add(ContributionItemFactory.REOPEN_EDITORS.create(getWindow()));
		menu.add(new GroupMarker(IWorkbenchActionConstants.MRU));
		menu.add(new Separator());


		IActionBarConfigurer2 actionBarConfigurer = (IActionBarConfigurer2) getActionBarConfigurer();
		coolBar.add(new GroupMarker(IIDEActionConstants.GROUP_FILE));
		{ // File Group
			IToolBarManager fileToolBar = actionBarConfigurer.createToolBarManager();
			fileToolBar.add(new Separator(IWorkbenchActionConstants.NEW_GROUP));
			fileToolBar.add(newWizardDropDownAction);
			fileToolBar.add(new GroupMarker(IWorkbenchActionConstants.NEW_EXT));
			fileToolBar.add(new GroupMarker(
					IWorkbenchActionConstants.SAVE_GROUP));
			fileToolBar.add(saveAction);
			fileToolBar.add(saveAllAction);
			fileToolBar
					.add(new GroupMarker(IWorkbenchActionConstants.SAVE_EXT));
			fileToolBar.add(getPrintItem());
			fileToolBar
					.add(new GroupMarker(IWorkbenchActionConstants.PRINT_EXT));

			fileToolBar
					.add(new Separator(IWorkbenchActionConstants.BUILD_GROUP));
			fileToolBar
					.add(new GroupMarker(IWorkbenchActionConstants.BUILD_EXT));
			fileToolBar.add(new Separator(
					IWorkbenchActionConstants.MB_ADDITIONS));

			coolBar.add(actionBarConfigurer.createToolBarContributionItem(fileToolBar,
					IWorkbenchActionConstants.TOOLBAR_FILE));
		}

		coolBar.add(new GroupMarker(IIDEActionConstants.GROUP_EDIT));
		{ // Edit group
			IToolBarManager editToolBar = actionBarConfigurer.createToolBarManager();
			editToolBar.add(new Separator(IWorkbenchActionConstants.EDIT_GROUP));
			editToolBar.add(undoAction);
			editToolBar.add(redoAction);

			coolBar.add(actionBarConfigurer.createToolBarContributionItem(editToolBar,
					IWorkbenchActionConstants.TOOLBAR_EDIT));
		}

		coolBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

		coolBar.add(new GroupMarker(IIDEActionConstants.GROUP_NAV));
		{ // Navigate group
			IToolBarManager navToolBar = actionBarConfigurer.createToolBarManager();
			navToolBar.add(new Separator(
					IWorkbenchActionConstants.HISTORY_GROUP));
			navToolBar
					.add(new GroupMarker(IWorkbenchActionConstants.GROUP_APP));
			navToolBar.add(backwardHistoryAction);
			navToolBar.add(forwardHistoryAction);
			navToolBar.add(new Separator(IWorkbenchActionConstants.PIN_GROUP));
			navToolBar.add(getPinEditorItem());

			coolBar.add(actionBarConfigurer.createToolBarContributionItem(navToolBar,
					IWorkbenchActionConstants.TOOLBAR_NAVIGATE));
		}

		coolBar.add(new GroupMarker(IWorkbenchActionConstants.GROUP_EDITOR));

		coolBar.add(new GroupMarker(IWorkbenchActionConstants.GROUP_HELP));

		{ // Help group
			IToolBarManager helpToolBar = actionBarConfigurer.createToolBarManager();
			helpToolBar.add(new Separator(IWorkbenchActionConstants.GROUP_HELP));

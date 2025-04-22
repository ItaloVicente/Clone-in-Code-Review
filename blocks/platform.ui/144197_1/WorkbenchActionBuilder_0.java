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

		try {
			page = new WorkbenchPage(this, input);
		} catch (WorkbenchException e) {
			WorkbenchPlugin.log(e);
		}

		ContextInjectionFactory.inject(page, model.getContext());
		windowContext.set(IWorkbenchPage.class, page);

		menuManager.setOverrides(menuOverride);
		((CoolBarToTrimManager) getCoolBarManager2()).setOverrides(toolbarOverride);

		fillActionBars(FILL_ALL_ACTION_BARS);
		firePageOpened();

		populateTopTrimContributions();
		populateBottomTrimContributions();

		modelService.getTrim(model, SideValue.LEFT);
		modelService.getTrim(model, SideValue.RIGHT);

		positionQuickAccess();

		Shell shell = (Shell) model.getWidget();
		if (model.getMainMenu() == null) {
			final MMenu mainMenu = MenuFactoryImpl.eINSTANCE.createMenu();

			final MenuManagerRenderer renderer = (MenuManagerRenderer) rendererFactory.getRenderer(
					mainMenu, null);
			renderer.linkModelToManager(mainMenu, menuManager);
			fill(renderer, mainMenu, menuManager);
			model.setMainMenu(mainMenu);
			final Menu menu = (Menu) engine.createGui(mainMenu, model.getWidget(),
					model.getContext());
			shell.setMenuBar(menu);

			menuUpdater = new Runnable() {
				public void run() {
					try {
						if (model.getMainMenu() == null || model.getWidget() == null
								|| menu.isDisposed() || mainMenu.getWidget() == null) {
							return;

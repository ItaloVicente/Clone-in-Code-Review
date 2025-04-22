		((WorkbenchWindow) configurer.getWindow()).fill(customizeActionBars.menuRenderer,
				customizeActionBars.mainMenu, customizeActionBars.menuManager);

		customizeActionBars.menuRenderer.reconcileManagerToModel(customizeActionBars.menuManager,
				customizeActionBars.mainMenu);
		customizeActionBars.windowModel.setMainMenu(customizeActionBars.mainMenu);
		context.get(IPresentationEngine.class).createGui(customizeActionBars.mainMenu,
				customizeActionBars.windowModel.getWidget(),
				customizeActionBars.windowModel.getContext());


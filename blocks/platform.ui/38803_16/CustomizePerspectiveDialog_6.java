		customizeActionBars.menuRenderer.reconcileManagerToModel(customizeActionBars.menuManager,
				customizeActionBars.mainMenu);

		IPresentationEngine engine = context.get(IPresentationEngine.class);
		engine.createGui(customizeActionBars.mainMenu, customizeActionBars.windowModel.getWidget(),
				customizeActionBars.windowModel.getContext());

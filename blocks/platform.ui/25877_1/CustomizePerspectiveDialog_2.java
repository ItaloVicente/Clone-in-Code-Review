

		MenuManager menuManager = customizeActionBars.menuManager;
		MMenu mainMenu = modelService.createModelElement(MMenu.class);
		mainMenu.setElementId("org.eclipse.ui.main.menu"); //$NON-NLS-1$
		menuMngrRenderer.linkModelToManager(mainMenu, menuManager);
		window.fill(menuMngrRenderer, mainMenu, menuManager);
		menuMngrRenderer.reconcileManagerToModel(menuManager, mainMenu);
		model.setMainMenu(mainMenu);
		engine.createGui(mainMenu, model.getWidget(), model.getContext());


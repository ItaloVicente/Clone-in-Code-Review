	public MMenu linkMenuManagerToMMenu(MenuManager mm) {
		final MMenu mainMenu = modelService.createModelElement(MMenu.class);
		mainMenu.setElementId("org.eclipse.ui.main.menu"); //$NON-NLS-1$

		final MenuManagerRenderer renderer = (MenuManagerRenderer) rendererFactory.getRenderer(
				mainMenu, null);
		renderer.linkModelToManager(mainMenu, mm);
		fill(renderer, mainMenu, mm);
		return mainMenu;
	}


			populateTopTrimContributions();
			populateBottomTrimContributions();

			modelService.getTrim(model, SideValue.LEFT);
			modelService.getTrim(model, SideValue.RIGHT);

			positionQuickAccess();

			Shell shell = (Shell) model.getWidget();
			if (model.getMainMenu() == null) {
				final MMenu mainMenu = MenuFactoryImpl.eINSTANCE.createMenu();
				mainMenu.setElementId("org.eclipse.ui.main.menu"); //$NON-NLS-1$

				final MenuManagerRenderer renderer = (MenuManagerRenderer) rendererFactory
						.getRenderer(mainMenu, null);
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
							}
							MenuManagerRendererFilter.updateElementVisibility(mainMenu, renderer,
									menuManager, windowContext.getActiveLeaf(), 1, false);
							menuManager.update(true);
						} finally {
							canUpdateMenus = true;

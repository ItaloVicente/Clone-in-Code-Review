		MenuLocationURI uri = new MenuLocationURI(location);
		if (!managers.containsKey(mgr)) {
			managers.put(mgr, uri);
		}

		if (mgr instanceof MenuManager) {
			MenuManager menu = (MenuManager) mgr;
			MMenu mMenu = getMenuModel(menu, uri);

			IRendererFactory factory = e4Context.get(IRendererFactory.class);
			AbstractPartRenderer obj = factory.getRenderer(mMenu, null);
			if (obj instanceof MenuManagerRenderer) {
				MenuManagerRenderer renderer = (MenuManagerRenderer) obj;
				mMenu.setRenderer(renderer);
				renderer.reconcileManagerToModel(menu, mMenu);
				renderer.processContributions(mMenu, false, false);
				renderer.processContents((MElementContainer<MUIElement>) ((Object) mMenu));
			}
		} else if (mgr instanceof ToolBarManager) {
			ToolBarManager toolbar = (ToolBarManager) mgr;
			MToolBar mToolBar = getToolbarModel(toolbar, uri);

			IRendererFactory factory = e4Context.get(IRendererFactory.class);
			AbstractPartRenderer obj = factory.getRenderer(mToolBar, null);
			if (obj instanceof ToolBarManagerRenderer) {
				ToolBarManagerRenderer renderer = (ToolBarManagerRenderer) obj;
				mToolBar.setRenderer(renderer);
				renderer.reconcileManagerToModel(toolbar, mToolBar);
				renderer.processContribution(mToolBar);
				renderer.processContents((MElementContainer<MUIElement>) ((Object) mToolBar));
			}
		} else {
			System.err.println("Unhandled manager: " + mgr); //$NON-NLS-1$
		}
	}

	protected MToolBar getToolbarModel(ToolBarManager toolbarManager, MenuLocationURI location) {
		MToolBar mToolBar = null;

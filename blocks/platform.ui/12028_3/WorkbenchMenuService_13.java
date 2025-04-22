	private void releaseContributionManager(ToolBarManager toolbarManager) {
		final IRendererFactory factory = e4Context.get(IRendererFactory.class);
		final AbstractPartRenderer obj = factory.getRenderer(
				MenuFactoryImpl.eINSTANCE.createToolBar(), null);
		if (!(obj instanceof ToolBarManagerRenderer)) {
			return;
		}
		ToolBarManagerRenderer renderer = (ToolBarManagerRenderer) obj;
		MToolBar mToolBar = renderer.getToolBarModel(toolbarManager);
		if (mToolBar == null) {
			return;
		}
		MApplicationElement model = (MApplicationElement) mToolBar.getTransientData().get(
				ModelUtils.CONTAINING_PARENT);
		if (model != null) {
			((Notifier) mToolBar).eAdapters().clear();
			ArrayList<MToolBar> toolbars = (ArrayList<MToolBar>) model.getTransientData().get(
					POPULATED_TOOL_BARS);
			if (toolbars != null) {
				toolbars.remove(mToolBar);
			}
		}
		final ToolBar widget = toolbarManager.getControl();
		if (widget != null && !widget.isDisposed()
				&& widget.getData(AbstractPartRenderer.OWNING_ME) == null) {
			widget.setData(AbstractPartRenderer.OWNING_ME, mToolBar);
		}
		final IPresentationEngine engine = e4Context.get(IPresentationEngine.class);
		engine.removeGui(mToolBar);
		mToolBar.getTransientData().remove(ModelUtils.CONTAINING_PARENT);
	}

	private void releaseContributionManager(MenuManager menuManager) {
		final IRendererFactory factory = e4Context.get(IRendererFactory.class);
		final AbstractPartRenderer obj = factory.getRenderer(((WorkbenchWindow) getWindow())
				.getModel().getMainMenu(), null);
		if (!(obj instanceof MenuManagerRenderer)) {
			return;
		}
		MenuManagerRenderer renderer = (MenuManagerRenderer) obj;
		MMenu mMenu = renderer.getMenuModel(menuManager);
		if (mMenu == null) {
			return;
		}
		MApplicationElement model = (MApplicationElement) mMenu.getTransientData().get(
				ModelUtils.CONTAINING_PARENT);
		if (model != null) {
			((Notifier) mMenu).eAdapters().clear();
			ArrayList<MMenu> menus = (ArrayList<MMenu>) model.getTransientData().get(
					POPULATED_MENUS);
			if (menus != null) {
				menus.remove(mMenu);
			}
		}
		final Menu widget = menuManager.getMenu();
		if (widget != null && !widget.isDisposed()
				&& widget.getData(AbstractPartRenderer.OWNING_ME) == null) {
			widget.setData(AbstractPartRenderer.OWNING_ME, mMenu);
		}
		final IPresentationEngine engine = e4Context.get(IPresentationEngine.class);
		engine.removeGui(mMenu);
		mMenu.getTransientData().remove(ModelUtils.CONTAINING_PARENT);

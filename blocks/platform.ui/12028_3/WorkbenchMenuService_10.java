		if (toolbars.contains(tb)) {
			return;
		}
		toolbars.add(tb);
		tb.getTransientData().put(ModelUtils.CONTAINING_PARENT, model);
		((Notifier) tb).eAdapters().add(ctx.get(UIEventPublisher.class));
	}

	private void addMenu(MApplicationElement model, MMenu menu, IEclipseContext ctx) {
		ArrayList<MMenu> menus = (ArrayList<MMenu>) model.getTransientData().get(POPULATED_MENUS);
		if (menus == null) {
			menus = new ArrayList<MMenu>();
			model.getTransientData().put(POPULATED_MENUS, menus);
		}
		if (menus.contains(menu)) {
			return;
		}
		menus.add(menu);
		menu.getTransientData().put(ModelUtils.CONTAINING_PARENT, model);
		((Notifier) menu).eAdapters().add(ctx.get(UIEventPublisher.class));
	}

	protected MMenu getMenuModel(MApplicationElement model, MenuManager menuManager,
			MenuLocationURI location) {

		final IRendererFactory factory = e4Context.get(IRendererFactory.class);
		final AbstractPartRenderer obj = factory.getRenderer(((WorkbenchWindow) getWindow())
				.getModel().getMainMenu(), null);
		if (!(obj instanceof MenuManagerRenderer)) {
			return null;
		}
		MenuManagerRenderer renderer = (MenuManagerRenderer) obj;
		MMenu mMenu = renderer.getMenuModel(menuManager);
		if (mMenu != null) {
			final String tag;
			if ("popup".equals(location.getScheme())) { //$NON-NLS-1$
				tag = "popup:" + location.getPath(); //$NON-NLS-1$
			} else {
				tag = "menu:" + location.getPath(); //$NON-NLS-1$

	private void addToolbar(MApplicationElement model, MToolBar tb, IEclipseContext ctx) {
		ArrayList<MToolBar> toolbars = (ArrayList<MToolBar>) model.getTransientData().get(
				POPULATED_TOOL_BARS);
		if (toolbars == null) {
			toolbars = new ArrayList<MToolBar>();
			model.getTransientData().put(POPULATED_TOOL_BARS, toolbars);
		}
		if (toolbars.contains(tb)) {
			return;
		}
		toolbars.add(tb);
		tb.getTransientData().put(ModelUtils.CONTAINING_CONTEXT, ctx);
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
		menu.getTransientData().put(ModelUtils.CONTAINING_CONTEXT, ctx);
		((Notifier) menu).eAdapters().add(ctx.get(UIEventPublisher.class));
	}


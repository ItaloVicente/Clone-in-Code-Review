	@Override
	public void clearContributions(PartSite site, MPart part) {
		List<MToolBar> toolbars = getContributedToolbars(part);
		IEclipseContext context = part.getContext();
		IRendererFactory rendererFactory = context.get(IRendererFactory.class);
		IActionBars actionBars = site.getActionBars();
		if (toolbars != null) {
			for (MToolBar mToolBar : toolbars) {
				((Notifier) mToolBar).eAdapters().clear();
				AbstractPartRenderer apr = rendererFactory.getRenderer(mToolBar, null);
				if (apr instanceof ToolBarManagerRenderer) {
					ToolBarManager tbm = (ToolBarManager) actionBars.getToolBarManager();
					ToolBarManagerRenderer tbmr = (ToolBarManagerRenderer) apr;
					tbmr.clearModelToManager(mToolBar, tbm);
					CompatibilityView.clearOpaqueToolBarItems(tbmr, mToolBar);
				}
				mToolBar.getTransientData().remove(ToolBarManagerRenderer.POST_PROCESSING_FUNCTION);
				final IPresentationEngine engine = context.get(IPresentationEngine.class);
				engine.removeGui(mToolBar);
				mToolBar.getTransientData().remove(ModelUtils.CONTAINING_PARENT);
			}
		}
		List<MMenu> menus = getContributedMenus(part);
		if (menus != null) {
			for (MMenu mMenu : menus) {
				((Notifier) mMenu).eAdapters().clear();
				AbstractPartRenderer apr = rendererFactory.getRenderer(mMenu, null);
				if (apr instanceof MenuManagerRenderer) {
					MenuManager tbm = (MenuManager) actionBars.getMenuManager();
					MenuManagerRenderer tbmr = (MenuManagerRenderer) apr;
					tbmr.clearModelToManager(mMenu, tbm);
					CompatibilityView.clearOpaqueMenuItems(tbmr, mMenu);
				}
				final IPresentationEngine engine = context.get(IPresentationEngine.class);
				engine.removeGui(mMenu);
				mMenu.getTransientData().remove(ModelUtils.CONTAINING_PARENT);
			}
		}
	}

	private List<MMenu> getContributedMenus(MPart part) {
		return (List<MMenu>) part.getTransientData().get(POPULATED_MENUS);
	}

	private List<MToolBar> getContributedToolbars(MPart part) {
		return (List<MToolBar>) part.getTransientData().get(POPULATED_TOOL_BARS);
	}


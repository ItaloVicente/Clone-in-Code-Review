		String populated_tb_id = "populatedToolBars"; //$NON-NLS-1$
		ArrayList<MToolBar> toolbars = (ArrayList<MToolBar>) part.getTransientData().get(populated_tb_id);
		if (toolbars != null) {
			for (MToolBar mToolBar : toolbars) {
				((Notifier) mToolBar).eAdapters().clear();
				AbstractPartRenderer apr = rendererFactory.getRenderer(mToolBar, null);
				if (apr instanceof ToolBarManagerRenderer) {
					ToolBarManager tbm = (ToolBarManager) actionBars.getToolBarManager();
					ToolBarManagerRenderer tbmr = (ToolBarManagerRenderer) apr;
					tbmr.clearModelToManager(mToolBar, tbm);
					clearOpaqueToolBarItems(tbmr, mToolBar);
				}
				mToolBar.getTransientData().remove(ToolBarManagerRenderer.POST_PROCESSING_FUNCTION);
				final IPresentationEngine engine = context.get(IPresentationEngine.class);
				engine.removeGui(mToolBar);
				mToolBar.getTransientData().remove(ModelUtils.CONTAINING_PARENT);
			}
			part.getTransientData().remove(populated_tb_id);
		}
		String populated_menu_id = "populatedMenus"; //$NON-NLS-1$
		ArrayList<MMenu> menus = (ArrayList<MMenu>) part.getTransientData().get(populated_menu_id);
		if (menus != null) {
			for (MMenu mMenu : menus) {
				((Notifier) mMenu).eAdapters().clear();
				AbstractPartRenderer apr = rendererFactory.getRenderer(mMenu, null);
				if (apr instanceof MenuManagerRenderer) {
					MenuManager tbm = (MenuManager) actionBars.getMenuManager();
					MenuManagerRenderer tbmr = (MenuManagerRenderer) apr;
					tbmr.clearModelToManager(mMenu, tbm);
					clearOpaqueMenuItems(tbmr, mMenu);
				}
				final IPresentationEngine engine = context.get(IPresentationEngine.class);
				engine.removeGui(mMenu);
				mMenu.getTransientData().remove(ModelUtils.CONTAINING_PARENT);
			}
			part.getTransientData().remove(populated_menu_id);
		}

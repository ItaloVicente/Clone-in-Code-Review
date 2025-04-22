	private void processOpaqueMenu(MenuManager parentManager, MMenu menuModel) {
		MenuManager manager = getManager(menuModel);
		if (manager != null) {
			return;
		}
		menuModel.setRenderer(this);
		Object obj = OpaqueElementUtil.getOpaqueItem(menuModel);
		if (obj instanceof MenuManager) {
			manager = (MenuManager) obj;
		} else {
			return;
		}
		manager.setVisible(menuModel.isVisible());
		addToManager(parentManager, menuModel, manager);
		linkModelToManager(menuModel, manager);
	}


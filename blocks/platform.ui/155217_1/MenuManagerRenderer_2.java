	private void processOpaqueMenu(MenuManager parentManager, MMenu menuModel) {
		MenuManager menuManager = getManager(menuModel);
		if (menuManager == null) {
			menuModel.setRenderer(this);
			Object obj = OpaqueElementUtil.getOpaqueItem(menuModel);
			if (obj instanceof MenuManager) {
				menuManager = (MenuManager) obj;
			} else {
				return;
			}
			menuManager.setVisible(menuModel.isVisible());
			addToManager(parentManager, menuModel, menuManager);
			linkModelToManager(menuModel, menuManager);
		}

		for (MMenuElement childME : menuModel.getChildren()) {
			modelProcessSwitch(menuManager, childME);
		}
	}


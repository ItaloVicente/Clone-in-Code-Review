		getParentMenuManager().updateAll(force);
	}

	protected SubMenuManager wrapMenu(IMenuManager menu) {
		SubMenuManager mgr = new SubMenuManager(menu);
		mgr.setVisible(isVisible());
		return mgr;
	}

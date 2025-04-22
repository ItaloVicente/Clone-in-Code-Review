		canvas.redraw();
	}

	private void createContextMenu() {
		MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(menuMgr1 -> fillMenu(menuMgr1));
		Menu menu = menuMgr.createContextMenu(this);
		setMenu(menu);

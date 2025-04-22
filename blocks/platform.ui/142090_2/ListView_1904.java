	}

	public void createDynamicPopupMenu() {
		menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(this);
		menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	public void createStaticPopupMenu() {
		menuMgr = new MenuManager();
		menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
		menuAboutToShow(menuMgr);
	}

	public void addElement(ListElement el) {
		input.add(el);
		viewer.refresh();
		viewer.getControl().update();
	}

	public void selectElement(ListElement el) {
		if (el == null) {

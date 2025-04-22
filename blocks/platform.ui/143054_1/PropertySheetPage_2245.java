			IToolBarManager toolBarManager, IStatusLineManager statusLineManager) {

		toolBarManager.add(categoriesAction);
		toolBarManager.add(filterAction);
		toolBarManager.add(defaultsAction);

		menuManager.add(categoriesAction);
		menuManager.add(filterAction);
		menuManager.add(columnsAction);

		viewer.setStatusLineManager(statusLineManager);
	}

	public void refresh() {
		if (viewer == null) {

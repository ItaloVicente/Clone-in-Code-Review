	public void addNavigationActions(IToolBarManager toolBar) {
		createActions();
		toolBar.add(homeAction);
		toolBar.add(backAction);
		toolBar.add(forwardAction);
		updateNavigationButtons();
	}

	public void addNavigationActions(IMenuManager manager) {
		createActions();
		manager.add(homeAction);
		manager.add(backAction);
		manager.add(forwardAction);
		updateNavigationButtons();
	}

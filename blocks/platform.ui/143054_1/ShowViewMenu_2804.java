
	@Override
	public void dispose() {
		if (menuManager != null) {
			menuManager.dispose();
			menuManager = null;
		}
		openedViews.clear();
		window = null;
		super.dispose();
	}

	@Override
	public void dispose() {
		super.dispose();
		if (menuManager != null) {
			menuManager.removeMenuListener(menuListener);
		}
		if (filtersMenu != null) {
			filtersMenu.dispose();
		}
	}

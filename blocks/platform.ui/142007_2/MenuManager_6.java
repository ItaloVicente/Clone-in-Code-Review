		MenuManagerEventHelper.getInstance().showEventPreHelper(this);
		fireAboutToShow(this);
		MenuManagerEventHelper.getInstance().showEventPostHelper(this);
		update(false, false);
	}

	private void handleAboutToHide() {
		MenuManagerEventHelper.getInstance().hideEventPreHelper(this);
		fireAboutToHide(this);
		MenuManagerEventHelper.getInstance().hideEventPostHelper(this);
	}

	private void initializeMenu() {
		menu.addMenuListener(new MenuAdapter() {
			@Override

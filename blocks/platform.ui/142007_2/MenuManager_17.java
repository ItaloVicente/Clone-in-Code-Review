		update(force, true);
	}

	private void updateMenuItem() {
		if (menuItem != null && !menuItem.isDisposed() && menuExist()) {
			boolean enabled = removeAllWhenShown || menu.getItemCount() > 0;
			if (menuItem.getEnabled() != enabled) {
				Menu topMenu = menu;
				while (topMenu.getParentMenu() != null) {

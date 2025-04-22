	/**
	 * Updates the menu item for this sub menu. The menu item is disabled if this
	 * sub menu is empty. Does nothing if this menu is not a submenu.
	 */
	private void updateMenuItem() {
		/*
		 * Commented out until proper solution to enablement of menu item for a sub-menu
		 * is found. See bug 30833 for more details.
		 *
		 * if (menuItem != null && !menuItem.isDisposed() && menuExist()) {
		 * IContributionItem items[] = getItems(); boolean enabled = false; for (int i =
		 * 0; i < items.length; i++) { IContributionItem item = items[i]; enabled =
		 * MenuItem.setEnabled() always causes a redraw if (menuItem.getEnabled() !=
		 * enabled) menuItem.setEnabled(enabled); }
		 */
		if (menuItem != null && !menuItem.isDisposed() && menuExist()) {
			boolean enabled = removeAllWhenShown || menu.getItemCount() > 0;
			if (menuItem.getEnabled() != enabled) {
				Menu topMenu = menu;
				while (topMenu.getParentMenu() != null) {
					topMenu = topMenu.getParentMenu();
				}
				if ((topMenu.getStyle() & SWT.BAR) == 0) {
					menuItem.setEnabled(enabled);
				}
			}
		}
	}


	public static MenuItem getMenuItem(MenuItem[] menuItems, String text) {
		for (MenuItem menuItem : menuItems) {
			if (menuItem.getText().startsWith(text)) {
				return menuItem;
			}
		}

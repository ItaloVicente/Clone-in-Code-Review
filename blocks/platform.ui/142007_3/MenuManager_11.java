	protected Item getMenuItem(int index) {
		if (menu != null) {
			return menu.getItem(index);
		}
		return null;
	}

	protected int getMenuItemCount() {
		if (menu != null) {
			return menu.getItemCount();
		}
		return 0;
	}


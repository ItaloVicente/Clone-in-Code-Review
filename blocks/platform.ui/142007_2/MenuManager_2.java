		listeners.add(listener);
	}

	public Menu createContextMenu(Control parent) {
		if (!menuExist()) {
			menu = new Menu(parent);
			menu.setData(MANAGER_KEY, this);
			initializeMenu();
		}
		return menu;
	}

	public Menu createMenuBar(Decorations parent) {
		if (!menuExist()) {
			menu = new Menu(parent, SWT.BAR);
			menu.setData(MANAGER_KEY, this);
			update(false);
		}
		return menu;
	}

	@Deprecated

		if (menu != null) {
			menu.dispose();
			menu = null;
		}
		if (isEnabled()) {
			menu = new Menu(parent);
			for (IAction action : getActions()) {
				ActionContributionItem item = new ActionContributionItem(
						action);
				item.fill(menu, -1);
			}
		}
		return menu;

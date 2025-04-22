			actions.add(desc);
		}

		public void contribute(IMenuManager menu, boolean menuAppendIfMissing, IToolBarManager toolbar,
				boolean toolAppendIfMissing) {
			if (menus != null && menu != null) {
				for (int i = 0; i < menus.size(); i++) {
					IConfigurationElement menuElement = (IConfigurationElement) menus.get(i);
					contributeMenu(menuElement, menu, menuAppendIfMissing);
				}
			}

			if (actions != null) {
				for (int i = 0; i < actions.size(); i++) {

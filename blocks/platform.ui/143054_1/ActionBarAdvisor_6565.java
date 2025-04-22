	public void fillActionBars(int flags) {
		if ((flags & FILL_PROXY) == 0) {
			makeActions(actionBarConfigurer.getWindowConfigurer().getWindow());
		}
		if ((flags & FILL_MENU_BAR) != 0) {
			fillMenuBar(actionBarConfigurer.getMenuManager());
			menuCreated = true;
		}
		if ((flags & FILL_COOL_BAR) != 0) {
			fillCoolBar(actionBarConfigurer.getCoolBarManager());
		}
		if ((flags & FILL_STATUS_LINE) != 0) {
			fillStatusLine(actionBarConfigurer.getStatusLineManager());
		}
	}

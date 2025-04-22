	public void fillActionBars(int flags) {
		if ((flags & FILL_PROXY) == 0) {
			makeActions(actionBarConfigurer.getWindowConfigurer().getWindow());
		}
		if ((flags & FILL_MENU_BAR) != 0) {
			fillMenuBar(actionBarConfigurer.getMenuManager());

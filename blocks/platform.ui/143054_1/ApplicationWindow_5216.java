	public ICoolBarManager getCoolBarManager2() {
		return coolBarManager;
	}

	protected Control getToolBarControl() {
		if (toolBarManager != null) {
			if (toolBarManager instanceof IToolBarManager2) {

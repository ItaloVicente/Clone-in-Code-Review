	private static synchronized WorkbenchThemeManager getInternalInstance() {
		if (instance == null) {
			instance = new WorkbenchThemeManager();
			instance.getCurrentTheme(); // initialize the current theme
		}
		return instance;
	}


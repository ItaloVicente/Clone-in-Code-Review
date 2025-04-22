		registerResourceLocator(new OSGiResourceLocator(
				"platform:/plugin/org.eclipse.ui.themes/css/"));
		registerResourceLocator(new FileResourcesLocatorImpl());
	}

	private String getVarientThemeId(String id, String os, String ws) {
		if (os != null) {
			id += '_' + os;
		}
		if (ws != null) {
			id += '-' + ws;

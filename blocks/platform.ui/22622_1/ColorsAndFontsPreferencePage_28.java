	private EventHandler themeRegistryRestyledHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			updateThemeInfo(workbench.getThemeManager());
			refreshCategory();
			refreshAllLabels();
		}
	};


	public void setTheme(ITheme theme, boolean restore, boolean force) {
		Assert.isNotNull(theme, "The theme must not be null");

		if (this.currentTheme != theme || force) {
			if (currentTheme != null) {
				for (IResourceLocator l : getResourceLocators(currentTheme.getId())) {
					for (CSSEngine engine : cssEngines) {
						engine.getResourcesLocatorManager().unregisterResourceLocator(l);

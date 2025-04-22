	private QuickAccessElement quickAccessElement(Display display, QuickAccessProvider provider, String elementId) {
		boolean providerRequiresUiAccess = provider.requiresUiAccess();
		if (providerRequiresUiAccess) {
			QuickAccessElement[] quickAccessElement = new QuickAccessElement[1];
			display.syncExec(() -> {
				quickAccessElement[0] = provider.getElementForId(elementId);
			});
			return quickAccessElement[0];
		}
		return provider.getElementForId(elementId);
	}


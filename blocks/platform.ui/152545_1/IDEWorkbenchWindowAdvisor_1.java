		propertyChangeListener = event -> {
			String property = event.getProperty();
			if (IDEInternalPreferences.WORKSPACE_NAME.equals(property)
					|| IDEInternalPreferences.SHOW_LOCATION.equals(property)
					|| IDEInternalPreferences.SHOW_LOCATION_NAME.equals(property)
					|| IDEInternalPreferences.SHOW_PERSPECTIVE_IN_TITLE.equals(property)
					|| IDEInternalPreferences.SHOW_PRODUCT_IN_TITLE.equals(property)) {
				lastActivePage = null;
				updateTitle(false);

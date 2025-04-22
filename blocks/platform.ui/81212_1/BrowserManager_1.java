		pcl = event -> {
			String property = event.getKey();
			if (!ignorePreferenceChanges && property.equals("browsers")) { //$NON-NLS-1$
				loadBrowsers();
			}
			if (!property.equals(WebBrowserPreference.PREF_INTERNAL_WEB_BROWSER_HISTORY)) {
				setChanged();
				notifyObservers();

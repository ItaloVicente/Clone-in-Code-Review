		pcl = new IEclipsePreferences.IPreferenceChangeListener() {

			@Override
			public void preferenceChange(PreferenceChangeEvent event) {
				String property = event.getKey();
					loadBrowsers();
				}
				if (!property.equals(WebBrowserPreference.PREF_INTERNAL_WEB_BROWSER_HISTORY)) {
					setChanged();
					notifyObservers();
				}

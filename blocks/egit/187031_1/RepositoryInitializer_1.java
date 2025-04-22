		if (gitCorePreferences != null) {
			if (prefsListener != null) {
				gitCorePreferences
						.removePreferenceChangeListener(prefsListener);
				prefsListener = null;
			}
			gitCorePreferences = null;
		}

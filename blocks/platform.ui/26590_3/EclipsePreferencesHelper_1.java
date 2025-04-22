	public static IPreferenceChangeListener getPreferenceChangeListener() {
		if (preferenceChangeListener == null) {
			preferenceChangeListener = new PreferenceOverriddenByCssChangeListener();
		}
		return preferenceChangeListener;
	}

	private static boolean isOverriddenByCSS(String propertiesOverriddenByCSS,
			String property) {
		return propertiesOverriddenByCSS.contains(SEPARATOR + property
				+ SEPARATOR);
	}


		String value = preferences.get(PROPS_OVERRIDDEN_BY_CSS_PROP, SEPARATOR);
		if (value.equals(SEPARATOR)) {
			preferences
			.addPreferenceChangeListener(getPreferenceChangeListener());
		}
		if (!isOverriddenByCSS(value, name)) {

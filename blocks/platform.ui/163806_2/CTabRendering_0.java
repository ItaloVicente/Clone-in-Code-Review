
		IEclipsePreferences preferences = getSwtRendererPreferences();
		preferences.addPreferenceChangeListener(this);
		parent.addDisposeListener(e -> preferences.removePreferenceChangeListener(this));

		cornerRadiusPreferenceChanged();

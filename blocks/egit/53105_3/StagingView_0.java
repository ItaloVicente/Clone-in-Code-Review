	private Presentation readPresentation(String key, Presentation def) {
		String presentationString = getPreferenceStore().getString(key);
		if (presentationString.length() > 0) {
			try {
				return Presentation.valueOf(presentationString);
			} catch (IllegalArgumentException e) {
			}
		}
		return def;
	}

	private void setPresentation(Presentation newOne, boolean auto) {
		Presentation old = presentation;
		presentation = newOne;
		IPreferenceStore store = getPreferenceStore();
		store.setValue(UIPreferences.STAGING_VIEW_PRESENTATION, newOne.name());
		if (auto && old != newOne) {
			store.setValue(UIPreferences.STAGING_VIEW_PRESENTATION_CHANGED,
					true);
		} else {
			store.setToDefault(UIPreferences.STAGING_VIEW_PRESENTATION_CHANGED);
		}
	}


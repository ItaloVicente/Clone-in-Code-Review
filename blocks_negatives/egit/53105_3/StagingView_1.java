		String presentationString = getPreferenceStore().getString(
				UIPreferences.STAGING_VIEW_PRESENTATION);
		if (presentationString.length() > 0) {
			try {
				presentation = Presentation.valueOf(presentationString);
			} catch (IllegalArgumentException e) {
			}
		}

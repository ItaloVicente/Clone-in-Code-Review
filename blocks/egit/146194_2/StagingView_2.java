		return getPresentation(getPreferenceStore().getString(key), def);
	}

	private static Presentation getPresentation(String value,
			Presentation def) {
		if (!value.isEmpty()) {

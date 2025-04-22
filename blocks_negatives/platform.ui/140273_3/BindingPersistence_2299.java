		final String defaultActiveSchemeId = store
				.getDefaultString(IWorkbenchPreferenceConstants.KEY_CONFIGURATION_ID);
		final String preferenceActiveSchemeId = store
				.getString(IWorkbenchPreferenceConstants.KEY_CONFIGURATION_ID);
		if ((preferenceActiveSchemeId != null)
				&& (!preferenceActiveSchemeId.equals(defaultActiveSchemeId))) {

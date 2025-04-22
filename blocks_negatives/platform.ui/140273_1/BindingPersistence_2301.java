		final String defaultSchemeId = store
				.getDefaultString(IWorkbenchPreferenceConstants.KEY_CONFIGURATION_ID);
		if ((defaultSchemeId == null) ? (scheme != null) : (!defaultSchemeId
				.equals(schemeId))) {
			store.setValue(IWorkbenchPreferenceConstants.KEY_CONFIGURATION_ID,
					scheme.getId());


		IUriSchemeExtensionReader extensionReader = testExtensionReader != null ? testExtensionReader
				: IUriSchemeExtensionReader.newInstance();

		IPreferencesService preferences = Platform.getPreferencesService();
		String schemes = testPreferenceNode != null ? testPreferenceNode.get(PROCESSED_SCHEMES_PREFERENCE, "") //$NON-NLS-1$
				: preferences.getString(UriSchemeExtensionReader.PLUGIN_ID, PROCESSED_SCHEMES_PREFERENCE, "", //$NON-NLS-1$
				null);


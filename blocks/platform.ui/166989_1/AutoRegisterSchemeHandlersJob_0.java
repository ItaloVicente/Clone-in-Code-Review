
		IUriSchemeExtensionReader extensionReader = IUriSchemeExtensionReader.newInstance();
		IPreferencesService preferences = Platform.getPreferencesService();
		String schemes = preferences.getString(UriSchemeExtensionReader.PLUGIN_ID, PROCESSED_SCHEMES_PREFERENCE, "", //$NON-NLS-1$
				null);


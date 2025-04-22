		ComboFieldEditor httpClient = new ComboFieldEditor(
				GitCorePreferences.core_httpClient,
				UIText.RemoteConnectionPreferencePage_HttpClientLabel,
				HTTP_CLIENT_NAMES_AND_VALUES, remoteConnectionsGroup) {

			@Override
			public IPreferenceStore getPreferenceStore() {
				return getSecondaryPreferenceStore();
			}

		};
		addField(httpClient);

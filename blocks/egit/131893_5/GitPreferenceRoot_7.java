		ComboFieldEditor sshClient = new ComboFieldEditor(
				GitCorePreferences.core_sshClient,
				UIText.RemoteConnectionPreferencePage_SshClientLabel,
				SSH_CLIENT_NAMES_AND_VALUES, remoteConnectionsGroup) {

			@Override
			public IPreferenceStore getPreferenceStore() {
				return getSecondaryPreferenceStore();
			}

		};
		addField(sshClient);

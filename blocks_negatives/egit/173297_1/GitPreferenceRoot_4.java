		ComboFieldEditor sshClient = new ComboFieldEditor(
				GitCorePreferences.core_sshClient,
				UIText.RemoteConnectionPreferencePage_SshClientLabel,
				SSH_CLIENT_NAMES_AND_VALUES, remoteConnectionsGroup) {

			@Override
			public void setPreferenceStore(IPreferenceStore store) {
				super.setPreferenceStore(
						store == null ? null : getSecondaryPreferenceStore());
			}
		};
		addField(sshClient);

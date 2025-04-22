		ConnectorFactory factory = ConnectorFactory.getDefault();
		if (factory != null) {
			boolean isWindows = SystemReader.getInstance().isWindows();
			useSshAgent = new BooleanFieldEditor(
					GitCorePreferences.core_sshAgent,
					UIText.GitPreferenceRoot_SshAgent_Label,
					remoteConnectionsGroup) {

				@Override
				public int getNumberOfControls() {
					return 2;
				}

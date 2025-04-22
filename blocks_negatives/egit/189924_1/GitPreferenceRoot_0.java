		boolean isWindows = SystemReader.getInstance().isWindows();
		BooleanFieldEditor useSshAgent = new BooleanFieldEditor(
				GitCorePreferences.core_sshAgent,
				isWindows ? UIText.GitPreferenceRoot_SshAgent_Pageant_Label
						: UIText.GitPreferenceRoot_SshAgent_Label,
				remoteConnectionsGroup) {

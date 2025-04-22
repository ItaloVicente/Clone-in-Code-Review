			addField(useSshAgent);
			Collection<ConnectorDescriptor> available = factory
					.getSupportedConnectors();
			if (available.size() > 1) {
				String[][] items = new String[available.size()][2];
				int i = 0;
				for (ConnectorDescriptor desc : available) {
					items[i][0] = desc.getDisplayName();
					items[i][1] = desc.getIdentityAgent();
					i++;
				}
				defaultSshAgent = new ComboFieldEditor(
						GitCorePreferences.core_sshDefaultAgent,
						UIText.GitPreferenceRoot_SshDefaultAgent_Label, items,
						remoteConnectionsGroup) {

		boolean isWindows = SystemReader.getInstance().isWindows();
		BooleanFieldEditor useSshAgent = new BooleanFieldEditor(
				GitCorePreferences.core_sshAgent,
				isWindows ? UIText.GitPreferenceRoot_SshAgent_Pageant_Label
						: UIText.GitPreferenceRoot_SshAgent_Label,
				remoteConnectionsGroup) {

			@Override
			public int getNumberOfControls() {
				return 2;
			}

			@Override
			public void setPreferenceStore(IPreferenceStore store) {
				super.setPreferenceStore(
						store == null ? null : getSecondaryPreferenceStore());
			}
		};
		if (!isWindows) {
			String productName = getProductName();
			useSshAgent.getDescriptionControl(remoteConnectionsGroup)
					.setToolTipText(MessageFormat.format(
							UIText.GitPreferenceRoot_SshAgent_Tooltip,
							productName));
		}
		addField(useSshAgent);

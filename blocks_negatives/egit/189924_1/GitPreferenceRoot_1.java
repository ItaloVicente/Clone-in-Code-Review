		};
		if (!isWindows) {
			String productName = getProductName();
			useSshAgent.getDescriptionControl(remoteConnectionsGroup)
					.setToolTipText(MessageFormat.format(
							UIText.GitPreferenceRoot_SshAgent_Tooltip,
							productName));

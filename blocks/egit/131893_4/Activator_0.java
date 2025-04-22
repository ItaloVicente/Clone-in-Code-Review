		sshClientChangeListener = event -> {
			if (GitCorePreferences.core_sshClient.equals(event.getKey())) {
				setupSSH(getBundle().getBundleContext());
			}
		};
		InstanceScope.INSTANCE.getNode(pluginId)
				.addPreferenceChangeListener(sshClientChangeListener);
		setupProxy();

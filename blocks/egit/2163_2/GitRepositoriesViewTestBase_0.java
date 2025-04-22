		PushConfiguredRemoteOperation pa = new PushConfiguredRemoteOperation(
				myRepository,
				new RemoteConfig(myRepository.getConfig(), "push"),
				org.eclipse.egit.ui.Activator.getDefault().getPreferenceStore()
						.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT));
		pa.execute(null);

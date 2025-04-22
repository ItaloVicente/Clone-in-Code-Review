	private void push() {
		RemoteConfig config = SimpleConfigurePushDialog
				.getConfiguredRemote(repo);
		if (config == null) {
			MessageDialog.openInformation(shell,
					UIText.SimplePushActionHandler_NothingToPushDialogTitle,
					UIText.SimplePushActionHandler_NothingToPushDialogMessage);
			return;
		}

		int timeout = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);
		PushOperationUI op = new PushOperationUI(repo, config.getName(),
				timeout, false);
		op.start();
	}


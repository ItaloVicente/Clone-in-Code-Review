		if (repository == null)
			return null;
		RemoteConfig config = SimpleConfigurePushDialog
				.getConfiguredRemote(repository);
		if (config == null) {
			MessageDialog.openInformation(getShell(event),
					UIText.SimplePushActionHandler_NothingToPushDialogTitle,
					UIText.SimplePushActionHandler_NothingToPushDialogMessage);

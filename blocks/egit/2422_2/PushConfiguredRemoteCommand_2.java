		if (config == null) {
			MessageDialog.openInformation(getShell(event),
					UIText.SimplePushActionHandler_NothingToPushDialogTitle,
					UIText.SimplePushActionHandler_NothingToPushDialogMessage);
			return null;
		}
		int timeout = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);

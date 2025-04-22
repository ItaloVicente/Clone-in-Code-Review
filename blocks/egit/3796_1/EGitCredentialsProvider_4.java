	}

	private boolean getMultiSpecial(Shell shell, URIish uri, CredentialItem... items) {
		CustomPromptDialog dialog = new CustomPromptDialog(shell, uri, UIText.EGitCredentialsProvider_information, items);
		dialog.setBlockOnOpen(true);
		int r = dialog.open();
		if (r == Window.OK) {
			return true;
		}
		return false;

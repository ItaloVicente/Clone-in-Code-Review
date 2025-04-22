		if (changeCredentials)
			getShell().setText(UIText.LoginDialog_changeCredentials);
		else if (promptText != null && promptText.length() > 0)
			getShell().setText(promptText);
		else
			getShell().setText(UIText.LoginDialog_login);

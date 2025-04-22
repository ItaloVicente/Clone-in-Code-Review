		switch (scheme.getSelectionIndex()) {
		case S_GIT:
			hostText.setEnabled(true);
			portText.setEnabled(true);
			setEnabledRecursively(authGroup, false);
			break;
		case S_SSH:
		case S_SFTP:
		case S_HTTP:
		case S_HTTPS:
		case S_FTP:
			hostText.setEnabled(true);
			portText.setEnabled(true);
			setEnabledRecursively(authGroup, true);
			break;
		case S_FILE:
			hostText.setEnabled(false);
			portText.setEnabled(false);
			setEnabledRecursively(authGroup, false);
			break;

		String t = nameText.getText();
		if (t.length() > 0
				&& !Repository.isValidRefName(Constants.R_REMOTES + t)) {
			setErrorMessage(NLS.bind(UIText.NewRemoteDialog_InvalidRemoteName,
					t));
			errorFound = true;
		}
		if (existingRemotes.contains(t)) {

		String t = getTrimmedRemoteName();
		if (t.length() > 0
				&& !Repository.isValidRefName(Constants.R_REMOTES + t)) {
			setErrorMessage(NLS.bind(UIText.NewRemoteDialog_InvalidRemoteName,
					t));
			errorFound = true;
		} else if (existingRemotes.contains(t)) {

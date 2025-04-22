		if (!Repository.isValidRefName(Constants.R_REMOTES + remoteName)) {
			setErrorMessage(NLS.bind(
					UIText.CloneDestinationPage_errorInvalidRemoteName,
					remoteName));
			setPageComplete(false);
			return;
		}

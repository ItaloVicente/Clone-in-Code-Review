
			String defaultBranch = getDefaultBranch();
			if (!Repository.isValidRefName(Constants.R_HEADS + defaultBranch)) {
				setErrorMessage(MessageFormat.format(
						UIText.CreateRepositoryPage_InvalidBranchName,
						defaultBranch));
			}

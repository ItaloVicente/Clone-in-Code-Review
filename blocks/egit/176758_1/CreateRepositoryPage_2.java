
			String defaultBranch = defaultBranchText.getText().trim();
			if (!Repository.isValidRefName(Constants.R_HEADS + defaultBranch)) {
				setErrorMessage(MessageFormat.format(
						UIText.CreateRepositoryPage_invalidBranchName,
						defaultBranch));
			}

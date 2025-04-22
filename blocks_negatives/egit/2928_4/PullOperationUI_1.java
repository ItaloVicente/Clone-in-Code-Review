			String shortBranchName = branchName.substring(Constants.R_HEADS
					.length());

			String remoteBranchName = repository.getConfig().getString(
					ConfigConstants.CONFIG_BRANCH_SECTION, shortBranchName,
					ConfigConstants.CONFIG_KEY_MERGE);
			if (remoteBranchName == null) {
				errorMessage = NLS
						.bind(
								UIText.PullOperationUI_BranchNotConfiguredForPullMessage,
								shortBranchName);
				return;

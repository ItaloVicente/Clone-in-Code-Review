		if (remoteBranchName == null && branchName != null) {
			remoteBranchName = repoConfig.getString(
					ConfigConstants.CONFIG_BRANCH_SECTION
					ConfigConstants.CONFIG_KEY_MERGE);
		}
		if (remoteBranchName == null) {
			remoteBranchName = branchName;
		}
		if (remoteBranchName == null) {
			throw new NoHeadException(
					JGitText.get().cannotCheckoutFromUnbornBranch);
		}

		boolean doRebase = false;
		if (remoteBranchName == null) {
			remoteBranchName = repoConfig.getString(
					ConfigConstants.CONFIG_BRANCH_SECTION, branchName,
					ConfigConstants.CONFIG_KEY_REBASE);
			if (remoteBranchName != null) {
				doRebase = true;
			}
		}

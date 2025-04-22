			return remoteName;
	}

	private String getMergeBranch() {
		String mergeRef = config.getString(
				ConfigConstants.CONFIG_BRANCH_SECTION, branchName,
				ConfigConstants.CONFIG_KEY_MERGE);
		return mergeRef;

		String branchName = Repository.shortenRefName(ref.getName());

		String remote = config.getString(ConfigConstants.CONFIG_BRANCH_SECTION,
				branchName, ConfigConstants.CONFIG_KEY_REMOTE);
		if (remote == null)
			return false;
		if (!remote.equals(remoteConfig.getName()))
			return true;

		String merge = config.getString(ConfigConstants.CONFIG_BRANCH_SECTION,
				branchName, ConfigConstants.CONFIG_KEY_MERGE);
		if (merge == null || !merge.equals(Constants.R_HEADS + getBranchName()))
			return true;

		boolean rebase = config.getBoolean(
				ConfigConstants.CONFIG_BRANCH_SECTION, branchName,
				ConfigConstants.CONFIG_KEY_REBASE, false);
		if (rebase != isRebaseSelected())
			return true;

		return false;

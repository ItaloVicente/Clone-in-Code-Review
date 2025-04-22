	private UpstreamConfig getDefaultUpstreamConfig(Repository repo,
			String refName) {
		String autosetupMerge = repo.getConfig().getString(
				ConfigConstants.CONFIG_BRANCH_SECTION, null,
				ConfigConstants.CONFIG_KEY_AUTOSETUPMERGE);
		if (autosetupMerge == null)
			autosetupMerge = ConfigConstants.CONFIG_KEY_TRUE;
		boolean isLocalBranch = refName.startsWith(Constants.R_HEADS);
		boolean isRemoteBranch = refName.startsWith(Constants.R_REMOTES);
		if (!isLocalBranch && !isRemoteBranch)
			return UpstreamConfig.NONE;
		boolean setupMerge = autosetupMerge
				.equals(ConfigConstants.CONFIG_KEY_ALWAYS)
				|| (isRemoteBranch && autosetupMerge
						.equals(ConfigConstants.CONFIG_KEY_TRUE));
		if (!setupMerge)
			return UpstreamConfig.NONE;
		String autosetupRebase = repo.getConfig().getString(
				ConfigConstants.CONFIG_BRANCH_SECTION, null,
				ConfigConstants.CONFIG_KEY_AUTOSETUPREBASE);
		if (autosetupRebase == null)
			autosetupRebase = ConfigConstants.CONFIG_KEY_NEVER;
		boolean setupRebase = autosetupRebase
				.equals(ConfigConstants.CONFIG_KEY_ALWAYS)
				|| (autosetupRebase.equals(ConfigConstants.CONFIG_KEY_LOCAL) && isLocalBranch)
				|| (autosetupRebase.equals(ConfigConstants.CONFIG_KEY_REMOTE) && isRemoteBranch);
		if (setupRebase)
			return UpstreamConfig.REBASE;
		return UpstreamConfig.MERGE;
	}


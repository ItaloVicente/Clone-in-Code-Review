	public static BranchRebaseMode getDefaultUpstreamConfig(Repository repo,
			String upstreamRefName) {
		boolean isLocalBranch = upstreamRefName.startsWith(Constants.R_HEADS);
		boolean isRemoteBranch = upstreamRefName
				.startsWith(Constants.R_REMOTES);
		if (!isLocalBranch && !isRemoteBranch) {
			return null;
		}
		String autosetupMerge = repo.getConfig().getString(
				ConfigConstants.CONFIG_BRANCH_SECTION, null,
				ConfigConstants.CONFIG_KEY_AUTOSETUPMERGE);
		if (autosetupMerge == null) {
			autosetupMerge = ConfigConstants.CONFIG_KEY_TRUE;
		}
		boolean setupMerge = autosetupMerge
				.equals(ConfigConstants.CONFIG_KEY_ALWAYS)
				|| (isRemoteBranch && autosetupMerge
						.equals(ConfigConstants.CONFIG_KEY_TRUE));
		if (!setupMerge) {
			return null;

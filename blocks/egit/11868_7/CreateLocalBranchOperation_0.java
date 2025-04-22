
		public static UpstreamConfig getDefault(Repository repo,
				String upstreamRefName) {
			String autosetupMerge = repo.getConfig().getString(
					ConfigConstants.CONFIG_BRANCH_SECTION, null,
					ConfigConstants.CONFIG_KEY_AUTOSETUPMERGE);
			if (autosetupMerge == null)
				autosetupMerge = ConfigConstants.CONFIG_KEY_TRUE;
			boolean isLocalBranch = upstreamRefName.startsWith(Constants.R_HEADS);
			boolean isRemoteBranch = upstreamRefName.startsWith(Constants.R_REMOTES);
			if (!isLocalBranch && !isRemoteBranch)
				return NONE;
			boolean setupMerge = autosetupMerge
					.equals(ConfigConstants.CONFIG_KEY_ALWAYS)
					|| (isRemoteBranch && autosetupMerge
							.equals(ConfigConstants.CONFIG_KEY_TRUE));
			if (!setupMerge)
				return NONE;
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
				return REBASE;
			return MERGE;
		}

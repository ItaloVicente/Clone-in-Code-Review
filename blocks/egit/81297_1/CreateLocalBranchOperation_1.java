		String autosetupRebase = repo.getConfig().getString(
				ConfigConstants.CONFIG_BRANCH_SECTION, null,
				ConfigConstants.CONFIG_KEY_AUTOSETUPREBASE);
		if (autosetupRebase == null) {
			autosetupRebase = ConfigConstants.CONFIG_KEY_NEVER;
		}
		boolean setupRebase = autosetupRebase
				.equals(ConfigConstants.CONFIG_KEY_ALWAYS)
				|| (autosetupRebase.equals(ConfigConstants.CONFIG_KEY_LOCAL)
						&& isLocalBranch)
				|| (autosetupRebase.equals(ConfigConstants.CONFIG_KEY_REMOTE)
						&& isRemoteBranch);
		if (setupRebase) {
			return BranchRebaseMode.REBASE;
		}
		return BranchRebaseMode.NONE;

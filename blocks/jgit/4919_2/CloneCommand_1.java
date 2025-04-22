		String autosetupRebase = clonedRepo.getConfig().getString(
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSETUPREBASE);
		if (ConfigConstants.CONFIG_KEY_ALWAYS.equals(autosetupRebase)
				|| ConfigConstants.CONFIG_KEY_REMOTE.equals(autosetupRebase))
			clonedRepo.getConfig().setBoolean(
					ConfigConstants.CONFIG_BRANCH_SECTION
					ConfigConstants.CONFIG_KEY_REBASE

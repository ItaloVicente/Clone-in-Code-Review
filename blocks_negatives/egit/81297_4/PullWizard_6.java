		if (this.page.isRebaseSelected()) {
			config.setBoolean(ConfigConstants.CONFIG_BRANCH_SECTION,
					localBranchName, ConfigConstants.CONFIG_KEY_REBASE, true);
		} else {
			config.unset(ConfigConstants.CONFIG_BRANCH_SECTION, localBranchName,
					ConfigConstants.CONFIG_KEY_REBASE);

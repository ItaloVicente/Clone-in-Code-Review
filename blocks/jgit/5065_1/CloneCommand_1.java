
		String autoSetupRebase = config.getString(
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSETUPREBASE);
		boolean shouldSetupRebase = ConfigConstants.CONFIG_KEY_ALWAYS
				.equals(autoSetupRebase)
				|| ConfigConstants.CONFIG_KEY_REMOTE.equals(autoSetupRebase);
		if (shouldSetupRebase)
			config.setBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
					branchName

		config.save();

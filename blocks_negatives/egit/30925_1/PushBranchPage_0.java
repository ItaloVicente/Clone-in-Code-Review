			String remoteName = config.getString(
					ConfigConstants.CONFIG_BRANCH_SECTION, branchName,
					ConfigConstants.CONFIG_KEY_REMOTE);

			String merge = config.getString(
					ConfigConstants.CONFIG_BRANCH_SECTION, branchName,
					ConfigConstants.CONFIG_KEY_MERGE);
			if (!isLocal && merge != null

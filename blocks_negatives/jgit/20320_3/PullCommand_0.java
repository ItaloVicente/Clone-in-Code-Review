		if (remoteBranchName == null) {
			String missingKey = ConfigConstants.CONFIG_BRANCH_SECTION + DOT
					+ branchName + DOT + ConfigConstants.CONFIG_KEY_MERGE;
			throw new InvalidConfigurationException(MessageFormat.format(
					JGitText.get().missingConfigurationForKey, missingKey));
		}

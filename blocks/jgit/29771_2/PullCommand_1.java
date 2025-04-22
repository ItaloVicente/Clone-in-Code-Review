
	private static PullRebaseMode getRebaseMode(String branchName
		PullRebaseMode mode = config.getEnum(PullRebaseMode.values()
				ConfigConstants.CONFIG_PULL_SECTION
				ConfigConstants.CONFIG_KEY_REBASE
		mode = config.getEnum(PullRebaseMode.values()
				ConfigConstants.CONFIG_BRANCH_SECTION
				branchName
		return mode;
	}

		return getRebaseMode() != BranchRebaseMode.NONE;
	}

	public BranchRebaseMode getRebaseMode() {
		return config.getEnum(BranchRebaseMode.values()
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REBASE

		String branchName = getCurrentBranch();
		if (branchName == null) {
			return;
		}
		Config cfg = repo.getConfig();
		BranchRebaseMode rebase = cfg.getEnum(BranchRebaseMode.values(),
				ConfigConstants.CONFIG_BRANCH_SECTION, branchName,
				ConfigConstants.CONFIG_KEY_REBASE, BranchRebaseMode.NONE);
		switch (rebase) {
		case PRESERVE:
			preserveMergesButton.setSelection(true);
			preserveMerges = true;
			break;
		case INTERACTIVE:
			interactiveButton.setSelection(true);
			interactive = true;
			break;
		default:
			break;
		}

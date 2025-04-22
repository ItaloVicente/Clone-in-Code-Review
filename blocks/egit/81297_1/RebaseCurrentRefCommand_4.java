			}
		} else {
			String branchName = Repository.shortenRefName(currentFullBranch);
			Config cfg = repository.getConfig();
			BranchRebaseMode rebase = cfg.getEnum(BranchRebaseMode.values(),
					ConfigConstants.CONFIG_BRANCH_SECTION, branchName,
					ConfigConstants.CONFIG_KEY_REBASE, BranchRebaseMode.NONE);
			preserveMerges = rebase == BranchRebaseMode.PRESERVE;
			interactive = rebase == BranchRebaseMode.INTERACTIVE;

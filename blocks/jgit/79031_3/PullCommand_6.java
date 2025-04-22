		pullRebaseMode = useRebase ? BranchRebaseMode.REBASE
				: BranchRebaseMode.NONE;
		return this;
	}

	public PullCommand setRebase(BranchRebaseMode rebaseMode) {
		checkCallable();
		pullRebaseMode = rebaseMode;

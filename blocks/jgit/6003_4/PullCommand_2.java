	public PullCommand setRebase(boolean useRebase) {
		checkCallable();
		pullRebaseMode = useRebase ? PullRebaseMode.REBASE : PullRebaseMode.NO_REBASE;
		return this;
	}


	public Optional execute() throws InvalidRemoteException {
		try {
			final List<Ref> branches = git._branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
			final Set<String> remoteBranches = new HashSet<>();
			final Set<String> localBranches = new HashSet<>();
			fillBranches(branches

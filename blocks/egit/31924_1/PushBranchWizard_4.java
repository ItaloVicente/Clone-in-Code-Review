	public PushBranchWizard(final Repository repository, Ref refToPush,
			boolean dryRun) {
		this(repository, refToPush);
		this.dryRun = dryRun;
	}


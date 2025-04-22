	private final String remoteName;

	public PushOperationUI(Repository repository, String remoteName,
			int timeout, boolean dryRun) {
		this.repository = repository;
		this.spec = null;
		this.config = null;
		this.remoteName = remoteName;
		this.timeout = timeout;
		this.dryRun = dryRun;
		destinationString = NLS.bind("{0} - {1}", repository.getDirectory() //$NON-NLS-1$
				.getParentFile().getName(), remoteName);
	}



	public PushOperation(Repository localDb,
			PushOperationSpecification specification, boolean dryRun,
			int timeout) {
		this(localDb, null, null, specification, dryRun, timeout);
	}

	public PushOperation(Repository localDb, String remoteName, boolean dryRun,
			int timeout) {
		this(localDb, remoteName, null, null, dryRun, timeout);

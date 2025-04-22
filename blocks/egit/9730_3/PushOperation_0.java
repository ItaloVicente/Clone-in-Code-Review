		this(localDb, remoteName, null, dryRun, timeout);
	}

	private PushOperation(final Repository localDb, final String remoteName,
			PushOperationSpecification specification, final boolean dryRun,
			int timeout) {

		this.rc = null;
		this.timeout = timeout;
	}

	public PushOperation(final Repository localDb, final RemoteConfig rc,
			final boolean dryRun, int timeout) {
		this.localDb = localDb;
		this.specification = null;
		this.dryRun = dryRun;

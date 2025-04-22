	public PullCommand setRemote(String remote) {
		checkCallable();
		remoteName = remote;
		return this;
	}


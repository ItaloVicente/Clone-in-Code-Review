	public PullCommand setRemote(String remote) {
		checkCallable();
		this.remote = remote;
		return this;
	}

	public PullCommand setRemoteBranchName(String remoteBranchName) {
		checkCallable();
		this.remoteBranchName = remoteBranchName;
		return this;
	}

	public String getRemote() {
		return remote;
	}

	public String getRemoteBranchName() {
		return remoteBranchName;
	}

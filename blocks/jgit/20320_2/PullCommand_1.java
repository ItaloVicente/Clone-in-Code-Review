	public PullCommand setRemote(String remote
		checkCallable();
		this.remote = remote;
		this.remoteBranchName = remoteBranchName;
		return this;
	}

	public String getRemote() {
		return remote;
	}

	public String getRemoteBranchName() {
		return remoteBranchName;
	}

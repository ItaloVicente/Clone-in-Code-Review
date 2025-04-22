	public CommitCommand setNoVerify(boolean noVerify) {
		this.noVerify = noVerify;
		return this;
	}

	public CommitCommand setHookOutputStream(PrintStream hookStdOut) {
		this.hookOutRedirect = hookStdOut;
		return this;
	}
